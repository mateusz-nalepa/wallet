//import android.content.Context
//import androidx.credentials.CredentialManager
//import androidx.credentials.GetCredentialRequest
//import androidx.credentials.GetCredentialResponse
//import androidx.credentials.GetPasswordOption
//import androidx.credentials.GetPublicKeyCredentialOption
//import androidx.credentials.PasswordCredential
//import androidx.credentials.PublicKeyCredential
//import androidx.credentials.exceptions.GetCredentialException
//import com.google.api.client.googleapis.json.GoogleJsonResponseException
//import com.google.api.client.http.FileContent
//import com.google.api.client.http.HttpRequestInitializer
//import com.google.api.client.http.javanet.NetHttpTransport
//import com.google.api.client.json.gson.GsonFactory
//import com.google.api.services.drive.Drive
//import com.google.api.services.drive.DriveScopes
//import com.google.api.services.drive.model.File
//import com.google.auth.http.HttpCredentialsAdapter
//import com.google.auth.oauth2.GoogleCredentials
//import java.io.IOException
//
///* Class to demonstrate use of Drive insert file API */
//
///**
// * Upload new file.
// *
// * @return Inserted file metadata if successful, `null` otherwise.
// * @throws IOException if service account credentials file not found.
// */
//
//
//fun handleSignIn(result: GetCredentialResponse) {
//    // Handle the successfully returned credential.
//    val credential = result.credential
//
//    when (credential) {
//        is PublicKeyCredential -> {
//            val responseJson = credential.authenticationResponseJson
//            // Share responseJson i.e. a GetCredentialResponse on your server to
//            // validate and  authenticate
//        }
//
//        is PasswordCredential -> {
//            val username = credential.id
//            val password = credential.password
//            // Use id and password to send to your server to validate
//            // and authenticate
//        }
//
//        else -> {
//            // Catch any unrecognized credential type here.
//            println("Unexpected type of credential")
//        }
//    }
//}
//
//suspend fun uploadBasic(context: Context): String {
//
//
//    val credentialManager = CredentialManager.create(context)
//
//    // Retrieves the user's saved password for your app from their
//// password provider.
//    val getPasswordOption = GetPasswordOption()
//
//// Get passkey from the user's public key credential provider.
////    val getPublicKeyCredentialOption =
////        GetPublicKeyCredentialOption(
////            requestJson = requestJson
////        )
//
//    val getCredRequest = GetCredentialRequest(
////        listOf(getPasswordOption, getPublicKeyCredentialOption)
//        listOf(getPasswordOption)
//    )
//
//    try {
//        val result = credentialManager.getCredential(
//            // Use an activity-based context to avoid undefined system UI
//            // launching behavior.
//            context = context,
//            request = getCredRequest
//        )
//        handleSignIn(result)
//    } catch (e : GetCredentialException) {
//        println(e)
//    }
//
//
//
//    // Load pre-authorized user credentials from the environment.
//    // TODO(developer) - See https://developers.google.com/identity for
//    // guides on implementing OAuth2 for your application.
//    val credentials: GoogleCredentials =
//        GoogleCredentials
//            .getApplicationDefault()
//            .createScoped(listOf(DriveScopes.DRIVE_FILE))
//    val requestInitializer: HttpRequestInitializer = HttpCredentialsAdapter(
//        credentials
//    )
//
//    // Build a new authorized API client service.
//    val service: Drive = Drive.Builder(
//        NetHttpTransport(),
//        GsonFactory.getDefaultInstance(),
//        requestInitializer
//    )
//        .setApplicationName("Drive samples")
//        .build()
//    // Upload file photo.jpg on drive.
//    val fileMetadata: File = File()
//    fileMetadata.setName("photo.jpg")
//    // File's content.
//    val filePath = java.io.File("files/photo.jpg")
//    // Specify media type and file-path for file.
//    val mediaContent = FileContent("image/jpeg", filePath)
//    return try {
//        val file: File = service.files().create(fileMetadata, mediaContent)
//            .setFields("id")
//            .execute()
//        println("File ID: " + file.id)
//        file.id
//    } catch (e: GoogleJsonResponseException) {
//        // TODO(developer) - handle error appropriately
//        System.err.println("Unable to upload file: " + e.details)
//        throw e
//    }
//}
