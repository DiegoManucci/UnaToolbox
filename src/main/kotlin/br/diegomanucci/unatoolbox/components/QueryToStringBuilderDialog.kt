package br.diegomanucci.unatoolbox.components

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.*
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.io.BufferedReader
import java.io.StringReader
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class QueryToStringBuilderDialog() : DialogWrapper(true) {
    private val leftTextArea = JTextArea(25, 35);
    private val rightTextArea = JTextArea(25, 35);

    init {
        title = "Query to StringBuilder"
        init()

        rightTextArea.isEditable = false
        leftTextArea.toolTipText = "Write/Paste the Query..."
        leftTextArea.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) {
                if (leftTextArea.text.equals("")) {
                    rightTextArea.text = ""
                    return
                }

                rightTextArea.text = parseQueryToStringBuilder(leftTextArea.text)
            }

            override fun removeUpdate(e: DocumentEvent?) {
                if (leftTextArea.text.equals("")) {
                    rightTextArea.text = ""
                    return
                }

                rightTextArea.text = parseQueryToStringBuilder(leftTextArea.text)
            }

            override fun changedUpdate(e: DocumentEvent?) {
                if (leftTextArea.text.equals("")) {
                    rightTextArea.text = ""
                    return
                }

                rightTextArea.text = parseQueryToStringBuilder(leftTextArea.text)
            }

        })

    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel();
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS);

        val contentPanel = JPanel();
        contentPanel.layout = BoxLayout(contentPanel, BoxLayout.X_AXIS);

        val leftLabel = JLabel("Query");
        leftLabel.horizontalAlignment = SwingConstants.CENTER;
        leftLabel.maximumSize = Dimension(Integer.MAX_VALUE, leftLabel.preferredSize.height);
        leftLabel.border = JBUI.Borders.emptyBottom(5)

        val leftScrollPane = JBScrollPane(leftTextArea)

        val leftPanel = JPanel();
        leftPanel.layout = BorderLayout(0, 0);
        leftPanel.isOpaque = false

        leftPanel.add(leftLabel, BorderLayout.NORTH);
        leftPanel.add(leftScrollPane, BorderLayout.CENTER);

        val rightLabel = JLabel("StringBuilder");
        rightLabel.horizontalAlignment = SwingConstants.CENTER;
        rightLabel.maximumSize = Dimension(Integer.MAX_VALUE, leftLabel.preferredSize.height);
        rightLabel.border = JBUI.Borders.emptyBottom(5)

        val rightScrollPane = JBScrollPane(rightTextArea)

        val rightPanel = JPanel();
        rightPanel.layout = BorderLayout(0, 0);
        rightPanel.isOpaque = false;

        rightPanel.add(rightLabel, BorderLayout.NORTH);
        rightPanel.add(rightScrollPane, BorderLayout.CENTER);

        contentPanel.add(leftPanel)
        contentPanel.add(Box.createRigidArea(Dimension(10, 0)))
        contentPanel.add(rightPanel)

        val buttonsPanel = JPanel(FlowLayout(FlowLayout.RIGHT))

        class CloseAction : AbstractAction("Close") {
            override fun actionPerformed(e: ActionEvent?) {
                close(CLOSE_EXIT_CODE)
            }
        }

        class CopyAction : AbstractAction("Copy") {
            override fun actionPerformed(e: ActionEvent?) {
                Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(rightTextArea.text), null)
                close(OK_EXIT_CODE)
            }
        }

        val closeButton = JButton(CloseAction())
        closeButton.text = "Close"

        val copyButton = JButton(CopyAction())
        copyButton.text = "Copy"
        copyButton.isOpaque = true

        buttonsPanel.add(copyButton)
        buttonsPanel.add(closeButton)

        panel.add(contentPanel)
        panel.add(Box.createRigidArea(Dimension(0, 10)))
        panel.add(buttonsPanel)

        return panel;
    }

    override fun createActions(): Array<Action> {
        return emptyArray()
    }

    private fun parseQueryToStringBuilder(queryString: String): String {
        var finalStringBuilderString = ""

        finalStringBuilderString += "StringBuilder query = new StringBuilder() ";

        val bufferedReader = BufferedReader(StringReader(queryString))

        var line = bufferedReader.readLine()
        while (line != null) {

            finalStringBuilderString += "\n.append(\"\\n $line\")"

            line = bufferedReader.readLine()
        }

        finalStringBuilderString += ";"

        return finalStringBuilderString
    }
}