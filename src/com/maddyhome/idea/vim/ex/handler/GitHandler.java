package com.maddyhome.idea.vim.ex.handler;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.ex.CommandHandler;
import com.maddyhome.idea.vim.ex.ExCommand;
import com.maddyhome.idea.vim.ex.ExException;
import com.maddyhome.idea.vim.ex.ExOutputModel;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class GitHandler extends CommandHandler {

    public GitHandler() {
        super("g", "it", RANGE_FORBIDDEN | ARGUMENT_OPTIONAL);
    }

    @Override
    public boolean execute(@NotNull Editor editor, @NotNull DataContext context,
                           @NotNull ExCommand cmd) throws ExException {
        final String argument = cmd.getArgument();

        StringBuilder script = new StringBuilder();
        script.append("cd " + editor.getProject().getBasePath() + "\n");
        script.append("git " + argument + "\n");

        ExOutputModel.getInstance(editor).output(getResultFromShell(script));
        return true;
    }

    private String getResultFromShell(StringBuilder script) {
        String result = "";
        try {
            ProcessBuilder pb = new ProcessBuilder("/bin/bash");
            Process bash = pb.start();

            PrintStream ps = new PrintStream(bash.getOutputStream());
            ps.println(script);
            ps.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(bash.getInputStream()));

            String line;
            while (null != (line = br.readLine())) {
                result += "> " + line + "\n";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}