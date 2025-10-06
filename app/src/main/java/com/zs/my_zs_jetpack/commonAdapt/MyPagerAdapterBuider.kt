import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.reflect.KClass

/**
 * 高级 ViewPager2 适配器构建器
 *
 * 使用示例：
 * val adapter = MyPagerAdapterBuilder(requireActivity())
 *     .addFragment(HomeFragment::class)
 *     .addFragment(TabFragment::class, Bundle().apply { putInt("type", 0) })
 *     .addFragment(SquareFragment::class)
 *     .addFragment(TabFragment::class, Bundle().apply { putInt("type", 1) })
 *     .addFragment(MineFragment::class)
 *     .enableCaching(true)
 *     .build()
 */
class MyPagerAdapterBuilder(private val activity: FragmentActivity) {

    private data class FragmentEntry(
        val fragmentClass: KClass<out Fragment>,
        val bundle: Bundle? = null
    )

    private val entries = mutableListOf<FragmentEntry>()
    private var enableCaching = false

    /**
     * 添加 Fragment 到适配器
     *
     * @param fragmentClass Fragment 类
     * @param bundle 可选参数 Bundle
     */
    fun addFragment(
        fragmentClass: KClass<out Fragment>,
        bundle: Bundle? = null
    ): MyPagerAdapterBuilder {
        entries.add(FragmentEntry(fragmentClass, bundle))
        return this
    }

    /**
     * 启用或禁用实例缓存
     *
     * @param enable 是否启用缓存（默认 true）
     */
    fun enableCaching(enable: Boolean = true): MyPagerAdapterBuilder {
        this.enableCaching = enable
        return this
    }

    /**
     * 构建最终的适配器实例
     */
    fun build(): FragmentStateAdapter {
        return MyPagerAdapter(
            activity = activity,
            fragmentClasses = entries.map { it.fragmentClass },
            bundles = entries.map { it.bundle },
            enableCaching = enableCaching
        )
    }

    /**
     * 内部实现的通用 ViewPager2 适配器
     */
    private class MyPagerAdapter(
        activity: FragmentActivity,
        private val fragmentClasses: List<KClass<out Fragment>>,
        private val bundles: List<Bundle?>,
        private val enableCaching: Boolean
    ) : FragmentStateAdapter(activity) {

        // 实例缓存
        private val fragmentCache = mutableMapOf<Int, Fragment>()

        init {
            // 验证参数
            require(fragmentClasses.size == bundles.size) {
                "Fragment classes and bundles must have the same size"
            }
        }

        override fun getItemCount(): Int = fragmentClasses.size

        override fun createFragment(position: Int): Fragment {
            // 如果启用缓存且缓存中有未添加的实例，则使用它
            val cachedFragment = fragmentCache[position]
            if (enableCaching && cachedFragment != null && !cachedFragment.isAdded) {
                return cachedFragment
            }

            // 创建新的 Fragment 实例
            val fragmentClass = fragmentClasses[position]
            val fragment = fragmentClass.java.newInstance()

            // 设置参数（如果有）
            bundles[position]?.let { args ->
                fragment.arguments = Bundle(args) // 克隆避免参数共享
            }

            // 缓存实例（如果需要）
            if (enableCaching) {
                fragmentCache[position] = fragment
            }

            return fragment
        }

        /**
         * 获取缓存的 Fragment 实例（如果存在）
         */
        fun getCachedFragment(position: Int): Fragment? {
            return fragmentCache[position]
        }

        /**
         * 清理缓存
         */
        fun clearCache() {
            fragmentCache.clear()
        }
    }
}