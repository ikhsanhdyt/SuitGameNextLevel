package com.ikhsanhdyt.suitgamenextlevel.ui.game

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.ActivityGameBinding
import com.ikhsanhdyt.suitgamenextlevel.enum.PlayerSide
import com.ikhsanhdyt.suitgamenextlevel.enum.SuitCharacter
import com.ikhsanhdyt.suitgamenextlevel.sharedPreferences.PlayerSharedPref
import com.ikhsanhdyt.suitgamenextlevel.usecase.SuitUseCase
import com.ikhsanhdyt.suitgamenextlevel.usecase.SuitUseCaseImpl
import com.ikhsanhdyt.suitgamenextlevel.utils.DialogUtils
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    //timer countdown for loading progress
    private val timer: CountDownTimer by lazy {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.visibility = View.VISIBLE
                binding.ivVs.visibility = View.GONE
            }

            override fun onFinish() {
                binding.progressBar.visibility = View.GONE
                binding.ivVs.visibility = View.VISIBLE
                checkWinner()
            }
        }
    }

    //view binding
    private lateinit var binding: ActivityGameBinding

    //suit usecase method
    private lateinit var suitUseCase: SuitUseCase

    //variable declaration
    private var player1: Int = SuitCharacter.ROCK.ordinal
    private var enemy: Int = SuitCharacter.ROCK.ordinal

    //global variable for shared pref player name
    private var playerName: String? = null

    //variable check gameplay mode
    private var gamePlayMode: Int = GAMEPLAY_MODE_VS_COMPUTER

    companion object {
        private const val EXTRAS_GAME_MODE = "EXTRAS_GAME_MODE"
        const val GAMEPLAY_MODE_VS_COMPUTER = 0
        const val GAMEPLAY_MODE_VS_PLAYER = 1

        fun startActivity(context: Context, gameplayMode: Int) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(EXTRAS_GAME_MODE, gameplayMode)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        getIntentData()
        suitUseCase = SuitUseCaseImpl()
        setUI()
        setOnClickListener()
    }

    private fun bindViews() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun getIntentData() {
        gamePlayMode = intent.extras?.getInt(EXTRAS_GAME_MODE, 0) ?: 0
    }

    private fun setUI() {
        playerName = PlayerSharedPref(this).playerName
        binding.tvPlayer.text = playerName

        if (gamePlayMode == GAMEPLAY_MODE_VS_PLAYER) {
            binding.constraintBgColor.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.green_player
                )
            )
            onSNACK(binding.root,"PLAYER 1 TURN")
            binding.tvEnemy.text = "Player 2"
        }else{
            binding.constraintBgColor.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.red_com
                )
            )
            onSNACK(binding.root,"SELECT YOUR CHARACTER")

        }
    }

    private fun setOnClickListener() {
        if (gamePlayMode == GAMEPLAY_MODE_VS_PLAYER) {
            binding.flBtnRightBatu.isEnabled = false
            binding.flBtnRightGunting.isEnabled = false
            binding.flBtnRightKertas.isEnabled = false
            binding.tvSelectGameEnemy.setOnClickListener {
                binding.flBtnRightBatu.isEnabled = false
                binding.flBtnRightGunting.isEnabled = false
                binding.flBtnRightKertas.isEnabled = false
                binding.tvSelectGameEnemy.visibility = View.INVISIBLE
                timer.start()
            }
            binding.flBtnLeftBatu.setOnClickListener() {
                player1 = SuitCharacter.ROCK.ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                binding.tvSelectGamePlayer.visibility = View.VISIBLE
                binding.tvSelectGamePlayer.setOnClickListener {
                    binding.flBtnRightBatu.isEnabled = true
                    binding.flBtnRightGunting.isEnabled = true
                    binding.flBtnRightKertas.isEnabled = true
                    binding.flBtnLeftBatu.isEnabled = false
                    binding.flBtnLeftGunting.isEnabled = false
                    binding.flBtnLeftKertas.isEnabled = false
                    binding.tvSelectGamePlayer.visibility = View.INVISIBLE
                    binding.tvSelectGameEnemy.visibility = View.VISIBLE
                    binding.llEnemy.isEnabled = true
                    onSNACK(binding.root, "Player 2 turn")
                    binding.flBtnLeftBatu.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                }
            }

            binding.flBtnLeftGunting.setOnClickListener() {
                player1 = SuitCharacter.SCISSOR.ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                binding.tvSelectGamePlayer.visibility = View.VISIBLE

                binding.tvSelectGamePlayer.setOnClickListener {
                    binding.flBtnRightBatu.isEnabled = true
                    binding.flBtnRightGunting.isEnabled = true
                    binding.flBtnRightKertas.isEnabled = true
                    binding.flBtnLeftBatu.isEnabled = false
                    binding.flBtnLeftGunting.isEnabled = false
                    binding.flBtnLeftKertas.isEnabled = false
                    binding.tvSelectGamePlayer.visibility = View.INVISIBLE
                    binding.tvSelectGameEnemy.visibility = View.VISIBLE
                    binding.llEnemy.isEnabled = true
                    onSNACK(binding.root, "Player 2 turn")
                    binding.flBtnLeftGunting.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                }
            }

            binding.flBtnLeftKertas.setOnClickListener() {
                player1 = SuitCharacter.PAPER.ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                binding.tvSelectGamePlayer.visibility = View.VISIBLE

                binding.tvSelectGamePlayer.setOnClickListener {
                    binding.flBtnRightBatu.isEnabled = true
                    binding.flBtnRightGunting.isEnabled = true
                    binding.flBtnRightKertas.isEnabled = true
                    binding.flBtnLeftBatu.isEnabled = false
                    binding.flBtnLeftGunting.isEnabled = false
                    binding.flBtnLeftKertas.isEnabled = false
                    binding.tvSelectGamePlayer.visibility = View.INVISIBLE
                    binding.tvSelectGameEnemy.visibility = View.VISIBLE
                    binding.llEnemy.isEnabled = true
                    onSNACK(binding.root, "Player 2 turn")
                    binding.flBtnLeftKertas.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                }
            }



            binding.flBtnRightBatu.setOnClickListener {
                enemy = SuitCharacter.ROCK.ordinal
                setPlayerMovement(PlayerSide.ENEMY, enemy)
            }

            binding.flBtnRightGunting.setOnClickListener {
                enemy = SuitCharacter.SCISSOR.ordinal
                setPlayerMovement(PlayerSide.ENEMY, enemy)
            }

            binding.flBtnRightKertas.setOnClickListener {
                enemy = SuitCharacter.PAPER.ordinal
                setPlayerMovement(PlayerSide.ENEMY, enemy)
            }

        } else {
            binding.flBtnLeftBatu.setOnClickListener() {
                onSNACK(binding.root, "Player choose ROCK")
                player1 = SuitCharacter.ROCK.ordinal
                enemy = SuitCharacter.values()[Random.nextInt(0, 3)].ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                timer.start()
            }
            binding.flBtnLeftGunting.setOnClickListener() {
                onSNACK(binding.root, "Player choose SCISSOR")
                player1 = SuitCharacter.SCISSOR.ordinal
                enemy = SuitCharacter.values()[Random.nextInt(0, 3)].ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                timer.start()
            }
            binding.flBtnLeftKertas.setOnClickListener() {
                onSNACK(binding.root, "Player choose PAPER")
                player1 = SuitCharacter.PAPER.ordinal
                enemy = SuitCharacter.values()[Random.nextInt(0, 3)].ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                timer.start()
            }
        }
    }

    private fun checkWinner() {
        when (suitUseCase.decideWinner(this.player1, this.enemy)) {
            SuitUseCaseImpl.DRAW -> {
                DialogUtils.showInputNameDialog(context = this, "DRAW") { dialog, value ->
                    dialog?.dismiss()
                    if (value == "menu") {
                        finish()
                    } else {
                        reset()
                    }
                }

            }
            SuitUseCaseImpl.PLAYER_ONE_WIN -> {
                DialogUtils.showInputNameDialog(
                    context = this,
                    "$playerName\nWIN"
                ) { dialog, value ->
                    dialog?.dismiss()
                    if (value == "menu") {
                        finish()
                    } else {
                        reset()
                    }
                }

            }
            SuitUseCaseImpl.PLAYER_TWO_WIN -> {
                DialogUtils.showInputNameDialog(context = this, "PLAYER 2\nWIN") { dialog, value ->
                    dialog?.dismiss()
                    if (value == "menu") {
                        finish()
                    } else {
                        reset()
                    }
                }

            }
        }
    }

    private fun setPlayerMovement(
        playerSide: PlayerSide,
        suitCharacter: Int
    ) {
        Log.d("player", "setPlayerMovement: " + playerSide)
        Log.d("suit_character", "setPlayerMovement: " + SuitCharacter.values()[suitCharacter])

        if (playerSide == PlayerSide.PLAYER1 && suitCharacter == SuitCharacter.ROCK.ordinal) {
            binding.flBtnLeftBatu.setBackgroundColor(
                ContextCompat.getColor(
                    this,


                    R.color.purple_200
                )
            )

            binding.flBtnLeftGunting.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnLeftKertas.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        } else if (playerSide == PlayerSide.PLAYER1 && suitCharacter == SuitCharacter.SCISSOR.ordinal) {
            binding.flBtnLeftBatu.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnLeftGunting.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.purple_200
                )
            )
            binding.flBtnLeftKertas.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        } else if (playerSide == PlayerSide.PLAYER1 && suitCharacter == SuitCharacter.PAPER.ordinal) {
            binding.flBtnLeftBatu.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnLeftGunting.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnLeftKertas.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.purple_200
                )
            )
        } else if (playerSide == PlayerSide.ENEMY && suitCharacter == SuitCharacter.ROCK.ordinal) {
            binding.flBtnRightBatu.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.purple_200
                )
            )
            binding.flBtnRightGunting.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnRightKertas.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        } else if (playerSide == PlayerSide.ENEMY && suitCharacter == SuitCharacter.SCISSOR.ordinal) {
            binding.flBtnRightBatu.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnRightGunting.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.purple_200
                )
            )
            binding.flBtnRightKertas.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        } else if (playerSide == PlayerSide.ENEMY && suitCharacter == SuitCharacter.PAPER.ordinal) {
            binding.flBtnRightBatu.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnRightGunting.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
            binding.flBtnRightKertas.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.purple_200
                )
            )
        }
    }

    private fun reset() {
        if (gamePlayMode == GAMEPLAY_MODE_VS_PLAYER){
            binding.tvSelectGamePlayer.visibility = View.VISIBLE
        }

        binding.flBtnLeftBatu.isEnabled = true
        binding.flBtnLeftGunting.isEnabled = true
        binding.flBtnLeftKertas.isEnabled = true

        binding.flBtnLeftBatu.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnLeftGunting.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnLeftKertas.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnRightBatu.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnRightGunting.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.flBtnRightKertas.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    }

    fun onSNACK(view: View, title: String) {
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, title,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.YELLOW)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.RED)
        textView.textSize = 18f
        snackbar.show()
    }

}