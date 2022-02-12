package com.ikhsanhdyt.suitgamenextlevel.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ikhsanhdyt.suitgameandroid.enum.PlayerSide
import com.ikhsanhdyt.suitgamenextlevel.R
import com.ikhsanhdyt.suitgamenextlevel.databinding.ActivityGameBinding
import com.ikhsanhdyt.usecase.SuitUseCase
import com.ikhsanhdyt.usecase.SuitUseCaseImpl
import com.ikhsanhdyt.utils.DialogUtils
import enum.SuitCharacter
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var suitUseCase: SuitUseCase


    private var player: Int = SuitCharacter.ROCK.ordinal
    private var computer: Int = SuitCharacter.values()[Random.nextInt(0, 3)].ordinal
    private var isGameFinished: Boolean = false

    private var gamePlayMode: Int = GAMEPLAY_MODE_VS_COMPUTER
//    private var playTurn : CharacterSide = CharacterSide.PLAYER

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

    private fun getIntentData() {
        gamePlayMode = intent.extras?.getInt(EXTRAS_GAME_MODE, 0) ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViews()
        getIntentData()
        suitUseCase = SuitUseCaseImpl()
        setState()
    }

    private fun bindViews() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    private fun setState() {
        if (!isGameFinished) {
            binding.flBtnLeftBatu.isEnabled = true
            binding.flBtnLeftGunting.isEnabled = true
            binding.flBtnLeftKertas.isEnabled = true
            setOnClickListener()
        } else {
            binding.flBtnLeftBatu.isEnabled = false
            binding.flBtnLeftGunting.isEnabled = false
            binding.flBtnLeftKertas.isEnabled = false
        }

//        //checking game mode when init state
//        if(gamePlayMode == GAMEPLAY_MODE_VS_PLAYER){
//            Toast.makeText(this, getString(R.string.text_toast_player_1_turn), Toast.LENGTH_SHORT).show()
//            showUIPlayer(CharacterSide.PLAYER,true)
//            showUIPlayer(CharacterSide.ENEMY,false)
//            //show lock Player 1
//            binding.tvStatusGame.text = getString(R.string.text_lock_player_1)
//        }else{
//            //set text for button fire
//            binding.tvStatusGame.text = getString(R.string.text_button_fire)
//        }

    }

    private fun setOnClickListener() {
        binding.flBtnLeftBatu.setOnClickListener() {
            player = SuitCharacter.ROCK.ordinal
            setPlayerMovement(PlayerSide.PLAYER1, player)
            setPlayerMovement(PlayerSide.PLAYER2, computer)
            checkWinner()
            isGameFinished = true
            setState()
        }
        binding.flBtnLeftGunting.setOnClickListener() {
            player = SuitCharacter.SCISSOR.ordinal
            setPlayerMovement(PlayerSide.PLAYER1, player)
            setPlayerMovement(PlayerSide.PLAYER2, computer)
            checkWinner()
            isGameFinished = true
            setState()
        }
        binding.flBtnLeftKertas.setOnClickListener() {
            player = SuitCharacter.PAPER.ordinal
            setPlayerMovement(PlayerSide.PLAYER1, player)
            setPlayerMovement(PlayerSide.PLAYER2, computer)
            checkWinner()
            isGameFinished = true
            setState()
        }
        binding.fbtnRefresh.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        isGameFinished = false
        binding.flBtnLeftBatu.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnLeftGunting.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnLeftKertas.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.flBtnRightBatu.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.ivVs.visibility = View.VISIBLE
        binding.tvP1Win.visibility = View.GONE
        binding.tvP2Win.visibility = View.GONE
        binding.tvDraw.visibility = View.GONE
        binding.flBtnRightGunting.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.flBtnRightKertas.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        setState()
    }

    private fun checkWinner() {
        when (suitUseCase.decideWinner(this.player, this.computer)) {
            SuitUseCaseImpl.DRAW -> {
                DialogUtils.showInputNameDialog(context = this,"DRAW"){ dialog,value->
                    dialog?.dismiss()
                    if (value == "menu"){
                        finish()
                    }else{
                        reset()
                    }
                }
                binding.tvDraw.visibility = View.VISIBLE
                binding.ivVs.visibility = View.GONE
                binding.tvP1Win.visibility = View.GONE
                binding.tvP2Win.visibility = View.GONE
            }
            SuitUseCaseImpl.PLAYER_ONE_WIN -> {
                binding.tvP1Win.visibility = View.VISIBLE
                binding.ivVs.visibility = View.GONE
                binding.tvDraw.visibility = View.GONE
                binding.tvP2Win.visibility = View.GONE
            }
            SuitUseCaseImpl.PLAYER_TWO_WIN -> {
                binding.tvP2Win.visibility = View.VISIBLE
                binding.ivVs.visibility = View.GONE
                binding.tvDraw.visibility = View.GONE
                binding.tvP1Win.visibility = View.GONE
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
        } else if (playerSide == PlayerSide.PLAYER2 && suitCharacter == SuitCharacter.ROCK.ordinal) {
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
        } else if (playerSide == PlayerSide.PLAYER2 && suitCharacter == SuitCharacter.SCISSOR.ordinal) {
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
        } else if (playerSide == PlayerSide.PLAYER2 && suitCharacter == SuitCharacter.PAPER.ordinal) {
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


}