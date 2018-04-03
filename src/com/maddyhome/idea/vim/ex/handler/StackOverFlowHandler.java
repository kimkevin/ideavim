package com.maddyhome.idea.vim.ex.handler;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.ex.CommandHandler;
import com.maddyhome.idea.vim.ex.ExCommand;
import com.maddyhome.idea.vim.ex.ExException;
import org.jetbrains.annotations.NotNull;

public class StackOverFlowHandler extends CommandHandler {

  public StackOverFlowHandler() {
    super("op", "en", RANGE_FORBIDDEN | ARGUMENT_REQUIRED);
  }

  @Override
  public boolean execute(@NotNull Editor editor, @NotNull DataContext context, @NotNull ExCommand cmd)
    throws ExException {
    final String argument = cmd.getArgument();
    BrowserUtil.browse("https://stackoverflow.com/search?q=" + argument);
    return false;
  }
}
