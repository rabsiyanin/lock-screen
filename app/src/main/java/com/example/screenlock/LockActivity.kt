package com.example.screenlock

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import android.widget.Toast

/**
 * Невидимая activity. Запускается по нажатию на значок приложения или на виджет,
 * просит службу заблокировать экран и сразу закрывается.
 * Если служба ещё не включена — подсказывает и открывает настройки спец. возможностей.
 */
class LockActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = LockService.instance
        when {
            service != null -> {
                if (!service.lock()) toast(R.string.lock_failed)
            }
            isServiceEnabled() -> toast(R.string.service_not_ready)
            else -> {
                toast(R.string.enable_hint)
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        finish()
        overridePendingTransition(0, 0)
    }

    /** Включена ли наша служба в настройках (даже если ещё не успела подключиться). */
    private fun isServiceEnabled(): Boolean {
        val manager = getSystemService(AccessibilityManager::class.java) ?: return false
        return manager
            .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .any { it.resolveInfo.serviceInfo.packageName == packageName }
    }

    private fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
    }
}
