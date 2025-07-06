package com.github.dnsv.relativeactions.keymap

abstract class AbstractKeymapEnum<T : Enum<T>> {
    private var keymapCache: Map<Char, T>? = null

    protected abstract fun createKeymap(): Map<Char, T>

    private fun getKeymap(): Map<Char, T> = keymapCache ?: createKeymap().also { keymapCache = it }

    fun containsKey(key: Char): Boolean = getKeymap().containsKey(key)

    fun getValue(key: Char): T = getKeymap().getValue(key)

    fun getKey(value: T): Char? = getKeymap().entries.firstOrNull { it.value == value }?.key

    fun invalidateCache() {
        keymapCache = null
    }
}
