package org.springboot.causeconnect.DTO;

public class FileSystemDto {
    private String bankStatementUrl;
    private String transcriptUrl;

    public FileSystemDto() {
    }

    public FileSystemDto(String bankStatementUrl, String transcriptUrl) {
        this.bankStatementUrl = bankStatementUrl;
        this.transcriptUrl = transcriptUrl;
    }

    public String getBankStatementUrl() {
        return bankStatementUrl;
    }

    public void setBankStatementUrl(String bankStatementUrl) {
        this.bankStatementUrl = bankStatementUrl;
    }

    public String getTranscriptUrl() {
        return transcriptUrl;
    }

    public void setTranscriptUrl(String transcriptUrl) {
        this.transcriptUrl = transcriptUrl;
    }

}
