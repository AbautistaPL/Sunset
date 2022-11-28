package com.raiserdev.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import com.raiserdev.sunset.databinding.ActivityMainBinding
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val  blueSkyColor: Int by lazy {
        ContextCompat.getColor(this,R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this,R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this,R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.scene.setOnClickListener {
            startAnimation()
        }

    }

    private fun startAnimation(){
        Log.d(TAG, "START_ANIMATION_")
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()

        val heightAnimation = ObjectAnimator
            .ofFloat(binding.sun,"y",sunYStart,sunYEnd)
            .setDuration(3000)
        heightAnimation.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(binding.sky,"backgroundColor",blueSkyColor,sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
            .ofInt(binding.sky,"backgroundColor",sunsetSkyColor,nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        /*heightAnimation.start()
        sunsetSkyAnimator.start()*/

        val animatorSet = AnimatorSet()
        animatorSet.apply {
            play(heightAnimation).with(sunsetSkyAnimator).before(nightSkyAnimator)
            start()
        }

    }

}