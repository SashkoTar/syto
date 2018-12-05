package com.sparja.syto.sound

import java.io.FileInputStream
import sun.audio.{AudioPlayer, AudioStream}

object WavePlayer {
  def main(args: Array[String]): Unit = {


    // open the sound file as a Java input stream
    val gongFile = "C:\\Projects\\syto\\src\\main\\resources\\nightingale.wav"
    val in = new FileInputStream(gongFile)

    // create an audiostream from the inputstream
    val audioStream = new AudioStream(in)

    // play the audio clip with the audioplayer class
    AudioPlayer.player.start(audioStream)
  }

}
