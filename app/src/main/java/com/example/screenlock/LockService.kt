package com.example.screenlock

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

/**
 * Служба специальных возможностей. Единственное, что она умеет, —
 * выполнить системное действие «заблокировать экран» (Android 9+).
 * Она не подписана ни на какие события и не читает содержимое экрана
 * (см. res/xml/accessibility_service_config.xml).
 */
class LockService : AccessibilityService() {

    companion object {
        @Volatile
        var instance: LockService? = null
            private set
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // событий не получаем
    }

    override fun onInterrupt() {
        // нечего прерывать
    }

    override fun onUnbind(intent: Intent?): Boolean {
        instance = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }

    /** Блокирует экран так же, как нажатие кнопки питания. Отпечаток/лицо продолжают работать. */
    fun lock(): Boolean = performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN)
}
