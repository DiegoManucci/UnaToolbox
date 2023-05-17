package br.diegomanucci.unatoolbox.actions

import br.diegomanucci.unatoolbox.components.QueryToStringBuilderDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class QueryToStringBuilder : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        val dialog = QueryToStringBuilderDialog();
        dialog.show();

    }

}