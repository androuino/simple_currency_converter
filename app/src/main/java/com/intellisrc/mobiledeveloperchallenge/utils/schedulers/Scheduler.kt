package com.intellisrc.mobiledeveloperchallenge.utils.schedulers

interface Scheduler {
    fun execute(runnable: Runnable?)
}