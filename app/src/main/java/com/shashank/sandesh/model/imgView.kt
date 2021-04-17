import android.app.Activity
import android.content.Intent
import android.net.Uri


class UIutils(private val mActivity: Activity) {
    fun showPhoto(photoUri: Uri?) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(photoUri, "image/*")
        mActivity.startActivity(intent)
    }
}