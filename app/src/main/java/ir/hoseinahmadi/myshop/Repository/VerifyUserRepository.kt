package ir.hoseinahmadi.myshop.Repository

import ir.hoseinahmadi.myshop.Remote.Email.VerifyEmailInterFace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named

class VerifyUserRepository @Inject constructor(
    @Named("VerifyInterFace")
    private val api: VerifyEmailInterFace
) {
    val messageSendCode = MutableStateFlow("")
    val loadingSendCode = MutableStateFlow(false)
    val resultSendCode = MutableStateFlow(false)
    val isError =MutableStateFlow(false)


    val messageVerifyCode = MutableStateFlow("")
    val loadingVerifyCode = MutableStateFlow(false)
    val resultVerify = MutableStateFlow(false)


    suspend fun senCodeeEmail(email: String) {
        loadingSendCode.emit(true)
        val responce = try {
            api.senCode(email)
        } catch (e: Exception) {
            messageSendCode.emit(e.message.toString())
            return
        }
        withContext(Dispatchers.Main) {
            if (responce.isSuccessful) {
                loadingSendCode.emit(false)
                messageSendCode.emit(responce.body()!!.message)
                resultSendCode.emit(true)
            } else {
                isError.emit(true)
                loadingSendCode.emit(false)
                messageSendCode.emit("ایمیل نا درست است")
            }
        }
    }

    suspend fun verifyCode(email: String, code: String) {
        loadingVerifyCode.emit(true)
        val responce = try {
            api.verify(
                anid = UUID.randomUUID().toString(),
                code = code,
                email = email
            )
        } catch (e: Exception) {
            messageVerifyCode.emit(e.message.toString())
            return
        }
        withContext(Dispatchers.Main) {
            if (responce.isSuccessful) {
                loadingVerifyCode.emit(false)
                messageVerifyCode.emit(responce.body()!!.message)
                resultVerify.emit(true)
            }
            else{
                loadingVerifyCode.emit(false)

            }
        }
    }


}