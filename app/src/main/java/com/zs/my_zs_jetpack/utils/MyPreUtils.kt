import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.zs.my_zs_jetpack.utils.BaseApp

object MyPreUtils {
    private const val PREFS_NAME = "my_app_prefs"

    private fun getPrefs(): SharedPreferences {
        return BaseApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }


    // 保存对象
    fun <T> setObject(key: String, bean: T) {
        val json = Gson().toJson(bean)
        getPrefs().edit().putString(key, json).apply()
    }

    // 读取对象
    fun <T> getObject(key: String, apiClass: Class<T>): T? {
        val json = getPrefs().getString(key, null)
        return if (json.isNullOrEmpty()) null else Gson().fromJson(json, apiClass)
    }

    // 清除数据（可选）
    fun clearKey(key: String) {
        getPrefs().edit().remove(key).apply()
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return getPrefs().getBoolean(key, default)
    }
}