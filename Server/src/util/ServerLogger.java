/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import domain.Radnik;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Dejan Colic
 */
public final class ServerLogger {

    private static ServerLogger instance;

    private final ReentrantLock lock = new ReentrantLock(true);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private volatile Path logFile;

    private ServerLogger() {
    }

    public static ServerLogger getInstance() {
        if (instance == null) {
            instance = new ServerLogger();
        }
        return instance;
    }

    public void init(Path logFilePath) throws IOException {
        if (logFilePath == null) {
            throw new IllegalArgumentException("logFilePath == null");
        }
        Path parent = logFilePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        if (!Files.exists(logFilePath)) {
            Files.createFile(logFilePath);
        }
        this.logFile = logFilePath.toAbsolutePath();
        logSystem("Logger initialized at: " + this.logFile);
    }

    public void logSystem(String message) {
        writeLine(format(null, message));
    }

    public void logAction(Radnik r, String message) {
        writeLine(format(r, message));
    }

    public void logError(Radnik r, String message, Throwable t) {
        String base = format(r, message);
        String err = (t == null) ? "" : (" | " + t.getClass().getSimpleName() + ": " + t.getMessage());
        writeLine(base + err);
    }

    public Path getLogFile() {
        return logFile;
    }

    private String format(Radnik r, String msg) {
        String ts = sdf.format(new Date());
        String who = (r == null) ? "SYSTEM"
                : ((safe(r.getIme()) + " " + safe(r.getPrezime())).trim());
        return "[" + ts + "] [" + who + "] " + (msg == null ? "" : msg);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private void writeLine(String line) {
        if (logFile == null) {
            throw new IllegalStateException("ServerLogger not initialized");
        }
        lock.lock();
        try (BufferedWriter bw = Files.newBufferedWriter(logFile,
                StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("ServerLogger write failed: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
