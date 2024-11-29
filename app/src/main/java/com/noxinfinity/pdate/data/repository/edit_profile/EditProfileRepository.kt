package com.noxinfinity.pdate.data.repository.edit_profile

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.DefaultUpload
import com.noxinfinity.pdate.DeletePictureByIdMutation
import com.noxinfinity.pdate.GetAllHobbiesQuery
import com.noxinfinity.pdate.GetAllPurposeQuery
import com.noxinfinity.pdate.GetListGradeQuery
import com.noxinfinity.pdate.GetListMajorQuery
import com.noxinfinity.pdate.UpdatePictureByIdMutation
import com.noxinfinity.pdate.UpdateUserInfoMutation
import com.noxinfinity.pdate.UpdateUserPurposeMutation
import com.noxinfinity.pdate.UploadAvatarMutation
import com.noxinfinity.pdate.UploadPictureMutation
import com.noxinfinity.pdate.type.Gender
import com.noxinfinity.pdate.utils.helper.ApolloHelper.Companion.getOptionalParam
import com.noxinfinity.pdate.utils.helper.UploadRequestBody
import com.noxinfinity.pdate.utils.toUpload
import java.io.File
import javax.inject.Inject

class EditProfileRepository @Inject constructor(
    private val client: ApolloClient,
) {
    suspend fun uploadAvatar(file: File) :Result<UploadAvatarMutation.Data> {
        return try {
            val body = UploadRequestBody(file, "image")

            val upload = DefaultUpload.Builder()
                .content(file.readBytes())
                .fileName(file.name)
                .contentType(body.contentType().toString())
                .build()

            val response = client.mutation(UploadAvatarMutation(upload)).execute()

            if (response.hasErrors()) {
                Log.d("EditProfileRepository Error", response.data?.uploadAvatar?.message ?: "Unknown Error")
            }

            val data = response.dataOrThrow()

            return Result.success(data)

        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun getAllPurpose() :Result<GetAllPurposeQuery.Data> {
        return try {
            val response = client.query(GetAllPurposeQuery()).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun updatePurpose(purposeIds: List<Int>) :Result<UpdateUserPurposeMutation.Data> {
        return try {
            val response = client.mutation(UpdateUserPurposeMutation(purposeIds)).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun uploadPicture(file: File) :Result<UploadPictureMutation.Data> {
        return try {

            val response = client.mutation(UploadPictureMutation(file.toUpload())).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun deletePictureById(id: String) :Result<DeletePictureByIdMutation.Data> {
        return try {

            val response = client.mutation(DeletePictureByIdMutation(id)).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun updatePictureById(file:File, id: String) :Result<UpdatePictureByIdMutation.Data> {
        return try {

            val response = client.mutation(UpdatePictureByIdMutation(file.toUpload(), id)).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun updateUserInfo(
        gender: Gender? = null,
        grade: Int? = null,
        major: Int? = null,
        bio: String? = null,
        dob: String? = null,
        email: String? = null,
        fullName: String? = null,
        hobbies: List<Int>? = null,
    ) :Result<UpdateUserInfoMutation.Data> {
        return try {
            val response = client.mutation(
                UpdateUserInfoMutation(
                    getOptionalParam(gender),
                    getOptionalParam(grade),
                    getOptionalParam(major),
                    getOptionalParam(bio),
                    getOptionalParam(dob),
                    getOptionalParam(email),
                    getOptionalParam(fullName),
                    getOptionalParam(hobbies),
                    ))
                .execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun getAllHobbies() :Result<GetAllHobbiesQuery.Data> {
        return try {
            val response = client.query(GetAllHobbiesQuery()).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun getListGrade() :Result<GetListGradeQuery.Data> {
        return try {
            val response = client.query(GetListGradeQuery()).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }

    suspend fun getListMajor() :Result<GetListMajorQuery.Data> {
        return try {
            val response = client.query(GetListMajorQuery()).execute()
            val data = response.dataOrThrow()
            return Result.success(data)
        } catch (e: Exception) {
            Log.d("EditProfileRepository Error", e.message ?: "")
            Result.failure(e)
        }
    }


}