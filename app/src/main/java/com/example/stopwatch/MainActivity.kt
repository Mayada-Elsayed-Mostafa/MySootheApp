package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private lateinit var stopWatch: Chronometer
    private var running = false
    private var offset: Long = 0

    private val OFFSETKEY = "offset"
    private val RUNNINGKEY = "running"
    private val BASEKEY = "base"

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //stopWatch = findViewById(R.id.stopwatch)

        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSETKEY)
            running = savedInstanceState.getBoolean(RUNNINGKEY)
            if(running){
                binding.stopwatch.base = savedInstanceState.getLong(BASEKEY)
                binding.stopwatch.start()
            } else setBaseTime()
        }

        //val startButton = findViewById<Button>(R.id.start)
        binding.start.setOnClickListener {
            if (!running){
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }
        }
        //val pauseButton = findViewById<Button>(R.id.pause)
        binding.pause.setOnClickListener {
            if (running){
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }
        }
        //val resetButton = findViewById<Button>(R.id.reset)
        binding.reset.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onPause() {
        super.onPause()
        if(running){
            saveOffset()
            binding.stopwatch.stop()
        }
    }
    override fun onResume() {
        super.onResume()
        if(running){
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }


    override fun onSaveInstanceState(savedInstanceState: Bundle){
        savedInstanceState.putLong(OFFSETKEY, offset)
        savedInstanceState.putBoolean(RUNNINGKEY, running)
        savedInstanceState.putLong(BASEKEY, binding.stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun setBaseTime(){
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }
    private fun saveOffset(){
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }
}