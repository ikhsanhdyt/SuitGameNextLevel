package com.ikhsanhdyt.suitgamenextlevel.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.ActivityGameBinding
import com.ikhsanhdyt.suitgamenextlevel.enum.PlayerSide
import com.ikhsanhdyt.suitgamenextlevel.enum.SuitCharacter
import com.ikhsanhdyt.suitgamenextlevel.sharedPreferences.PlayerSharedPref
import com.ikhsanhdyt.suitgamenextlevel.usecase.SuitUseCase
import com.ikhsanhdyt.suitgamenextlevel.usecase.SuitUseCaseImpl
import com.ikhsanhdyt.suitgamenextlevel.utils.DialogUtils

class GameActivity : AppCompatActivity() {
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
        setState()
        setOnClickListener()
    }

    private fun getIntentData() {
        gamePlayMode = intent.extras?.getInt(EXTRAS_GAME_MODE, 0) ?: 0
    }

    private fun bindViews() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun setState() {
        if (gamePlayMode == GAMEPLAY_MODE_VS_PLAYER) {
            Toast.makeText(this, getString(R.string.text_toast_player_1_turn), Toast.LENGTH_SHORT)
                .show()
            showUIPlayer(PlayerSide.PLAYER1, true)
            showUIPlayer(PlayerSide.ENEMY, false)
        } else {
            showUIPlayer(PlayerSide.PLAYER1, true)

        }
    }

    private fun showUIPlayer(playerSide: PlayerSide, isEnable: Boolean) {
        playerName = PlayerSharedPref(this).playerName

        if (playerSide == PlayerSide.PLAYER1) {
            binding.tvPlayer.text = playerName
        } else {
            binding.tvEnemy.text = "Player 2"
        }
    }

    private fun setOnClickListener() {
        if (gamePlayMode == GAMEPLAY_MODE_VS_PLAYER) {
            binding.flBtnRightBatu.isEnabled = false
            binding.flBtnRightGunting.isEnabled = false
            binding.flBtnRightKertas.isEnabled = false
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
                    binding.tvSelectGamePlayer.visibility = View.VISIBLE
                    Toast.makeText(this, "Player 2 turn", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "Player 2 turn", Toast.LENGTH_SHORT).show()
                    binding.flBtnLeftKertas.setBackgroundColor(
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
                    Toast.makeText(this, "Player 2 turn", Toast.LENGTH_SHORT).show()
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
                checkWinner()
            }

            binding.flBtnRightGunting.setOnClickListener {
                enemy = SuitCharacter.SCISSOR.ordinal
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                checkWinner()
            }

            binding.flBtnRightKertas.setOnClickListener {
                enemy = SuitCharacter.PAPER.ordinal
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                checkWinner()
            }

        } else {
            binding.flBtnLeftBatu.setOnClickListener() {
                player1 = SuitCharacter.ROCK.ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                checkWinner()
                setState()
            }
            binding.flBtnLeftGunting.setOnClickListener() {
                player1 = SuitCharacter.SCISSOR.ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                checkWinner()
                setState()
            }
            binding.flBtnLeftKertas.setOnClickListener() {
                player1 = SuitCharacter.PAPER.ordinal
                setPlayerMovement(PlayerSide.PLAYER1, player1)
                setPlayerMovement(PlayerSide.ENEMY, enemy)
                checkWinner()
                setState()
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
        setState()
    }
}