package org.springboot.causeconnect.entities;

import jakarta.persistence.*;

@Entity
public class FileSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Lob
    byte[] bankStatement;
    @Lob
    byte[] transcript;
    @Lob
    byte[] profilePic;
    public FileSystem(){

    }

    public FileSystem(int id, byte[] bankStatement, byte[] transcript, byte[] profilePic) {
        this.id = id;
        this.bankStatement = bankStatement;
        this.transcript = transcript;
        this.profilePic = profilePic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(byte[] bankStatement) {
        this.bankStatement = bankStatement;
    }

    public byte[] getTranscript() {
        return transcript;
    }

    public void setTranscript(byte[] transcript) {
        this.transcript = transcript;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
}
