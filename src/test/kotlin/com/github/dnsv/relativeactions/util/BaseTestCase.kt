package com.github.dnsv.relativeactions.util

import com.intellij.notification.ActionCenter
import com.intellij.notification.Notification
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.psi.PsiFile
import com.intellij.testFramework.LightProjectDescriptor
import com.intellij.testFramework.fixtures.CodeInsightTestFixture
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl
import com.intellij.testFramework.junit5.RunInEdt
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import java.awt.datatransfer.DataFlavor

@RunInEdt(writeIntent = true)
abstract class BaseTestCase {
    protected lateinit var myFixture: CodeInsightTestFixture

    @BeforeEach
    fun baseBeforeEach() {
        myFixture = createFixture()
        myFixture.testDataPath = getTestDataPath()
        myFixture.setUp()
    }

    @AfterEach
    fun baseAfterEach() {
        myFixture.tearDown()
    }

    private fun createFixture(): CodeInsightTestFixture {
        val projectDescriptor = LightProjectDescriptor.EMPTY_PROJECT_DESCRIPTOR
        val factory = IdeaTestFixtureFactory.getFixtureFactory()
        val fixtureBuilder = factory.createLightFixtureBuilder(projectDescriptor, "relative-actions")
        val fixture = fixtureBuilder.fixture

        return factory.createCodeInsightFixture(fixture, LightTempDirTestFixtureImpl(true))
    }

    private fun getTestDataPath(): String = PathManager.getHomePath() + "/community/plugins/relative-actions/testData"

    protected fun makeEditor(text: String): PsiFile = myFixture.configureByText(PlainTextFileType.INSTANCE, text)

    protected fun getEditor(): Editor = myFixture.editor

    protected fun simulateTyping(input: String) = input.forEach { char -> myFixture.type(char) }

    protected fun getLastNotification(): Notification = ActionCenter.getNotifications(myFixture.project).last()

    protected fun performCommand(command: String) {
        myFixture.performEditorAction("ActivateRelativeActions")
        simulateTyping(command)
    }

    protected fun moveCaret(position: LogicalPosition) {
        getEditor().caretModel.moveToLogicalPosition(position)
    }

    protected fun assertCaretPosition(position: LogicalPosition) {
        Assertions.assertEquals(position, getEditor().caretModel.logicalPosition)
    }

    protected fun assertSelection(expected: String) {
        val selected = getEditor().selectionModel.selectedText
        Assertions.assertEquals(expected, selected)
    }

    protected fun assertClipboard(expected: String) {
        val clipboardContent = CopyPasteManager.getInstance().getContents(DataFlavor.stringFlavor) as? String
        Assertions.assertEquals(expected, clipboardContent)
    }

    protected fun assertText(expected: String) {
        Assertions.assertEquals(expected, getEditor().document.text)
    }
}
