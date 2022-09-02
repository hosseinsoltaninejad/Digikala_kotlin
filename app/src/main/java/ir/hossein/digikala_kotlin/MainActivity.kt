package ir.hossein.digikala_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import ir.hossein.digikala_kotlin.cat.CatFragment
import ir.hossein.digikala_kotlin.home.HomeFragment
import ir.hossein.digikala_kotlin.profile.ProfileFragment
import ir.hossein.digikala_kotlin.cart.CartFragment
import ir.hossein.digikala_kotlin.data.CartItemCount
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    private val mainViewModel:MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main )

        EventBus.getDefault().register(this)

        setUpViews()
        firstRun()

        bottomNavigationView.setOnItemSelectedListener {

            when ( it.itemId ) {

                R.id.home -> {
                    replaceFragment( HomeFragment() )
                }
                R.id.cat -> {
                    replaceFragment( CatFragment() )
                }
                R.id.shop -> {
                    replaceFragment( CartFragment() )
                }
                R.id.profile -> {
                    replaceFragment( ProfileFragment() )
                }
            }

            true
        }
        bottomNavigationView.setOnItemReselectedListener {}
    }

    private fun setUpViews() {
        bottomNavigationView = findViewById(R.id.bottom_navagation)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace( R.id.nav_host_container , fragment )
        transaction.commit()
    }

    private fun firstRun() {
        replaceFragment( HomeFragment() )
        bottomNavigationView.selectedItemId = R.id.home
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun cartItemCountEvent(cartItemCount: CartItemCount){
        val badge=bottomNavigationView.getOrCreateBadge(R.id.shop)
        badge.backgroundColor=MaterialColors.getColor(bottomNavigationView, android.R.attr.colorPrimary)
        badge.badgeGravity= BadgeDrawable.BOTTOM_START
        badge.number=cartItemCount.count.toInt()
        badge.isVisible=cartItemCount.count.toInt()>0
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getCartItemCount()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    }