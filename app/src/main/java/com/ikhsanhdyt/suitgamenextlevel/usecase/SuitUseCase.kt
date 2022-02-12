package com.ikhsanhdyt.suitgamenextlevel.usecase

interface SuitUseCase {
    fun decideWinner(playerOne: Int, playerTwo: Int) : Int
}