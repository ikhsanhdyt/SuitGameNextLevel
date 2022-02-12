package com.ikhsanhdyt.usecase

import enum.SuitCharacter

class SuitUseCaseImpl : SuitUseCase {

    companion object{
        const val DRAW = 0
        const val PLAYER_ONE_WIN = 1
        const val PLAYER_TWO_WIN = 2
    }

    override fun decideWinner(playerOne: Int, playerTwo: Int) : Int {
        return if(playerOne == playerTwo){
            DRAW
        }else if(playerOne == SuitCharacter.ROCK.ordinal){
            if(playerTwo == SuitCharacter.PAPER.ordinal){
                PLAYER_TWO_WIN
            }else{
                PLAYER_ONE_WIN
            }
        }else if(playerOne == SuitCharacter.PAPER.ordinal){
            if(playerTwo == SuitCharacter.SCISSOR.ordinal){
                PLAYER_TWO_WIN
            }else{
                PLAYER_ONE_WIN
            }
        }else{
            //if player 1 pick scissor
            if(playerTwo == SuitCharacter.ROCK.ordinal){
                PLAYER_TWO_WIN
            }else{
                PLAYER_ONE_WIN
            }
        }
    }


}