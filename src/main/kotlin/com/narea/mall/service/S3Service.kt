package com.narea.mall.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.narea.mall.exception.BadRequestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.net.URLEncoder

@Service
class S3Service(private val amazonS3: AmazonS3,
                @Value("\${narea-mall.aws.s3.bucketName}")
                private val bucketName: String
) {
    fun validateMultipartFile(target:String, id:Long, file:MultipartFile) {
        if(file.isEmpty) throw BadRequestException("$target $id is empty")
    }
    fun create(uploadFile: MultipartFile, dirName: String): String {
        val originalFilename = uploadFile.originalFilename
        if (originalFilename.isNullOrEmpty()) {
            throw BadRequestException("Upload file name is empty")
        }
        // 업로드 된 파일의 s3 URL 주소 반환
        return putS3(
            uploadFile = uploadFile ,
            fileName =  "$dirName/${originalFilename.encodeUtf8()}"
        )
    }

    private fun putS3(uploadFile: MultipartFile, fileName: String): String =
         ObjectMetadata().apply {
            contentType = uploadFile.contentType
            contentLength = uploadFile.size
         }.let { objectMetadata ->
            amazonS3.putObject(
                PutObjectRequest(
                    bucketName, fileName, uploadFile.inputStream, objectMetadata
                )
            )
            amazonS3.getUrl(bucketName, fileName).toString()
        }


    fun delete(fileUrl: String) {
        if (fileUrl.isEmpty()) return
        val fileName = URI(fileUrl).path.removePrefix("/")
        amazonS3.deleteObject(
            DeleteObjectRequest(
                bucketName,
                fileName
            )
        )
    }

    private fun String.encodeUtf8() = URLEncoder.encode(this, Charsets.UTF_8)
}

