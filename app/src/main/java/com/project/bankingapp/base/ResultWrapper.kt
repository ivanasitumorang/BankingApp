package com.project.bankingapp.base

suspend fun <T> suspendTryCatch(
    codeBlock: suspend () -> Result<T>
): Result<T> = try {
    codeBlock.invoke()
} catch (e: Exception) {
    Result.Error(code = -1, e)
}