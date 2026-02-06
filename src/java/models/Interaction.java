/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Hao
 */
public class Interaction {
    private long interactionId;
    private int leadId;
    private int agentId;
    private String channel;
    private String content;
    private Timestamp ts;
    private String outcome;
    private Timestamp nextActionAt; // nullable

    public Interaction() {}

    public Interaction(long interactionId, int leadId, int agentId, String channel, String content,
                       Timestamp ts, String outcome, Timestamp nextActionAt) {
        this.interactionId = interactionId;
        this.leadId = leadId;
        this.agentId = agentId;
        this.channel = channel;
        this.content = content;
        this.ts = ts;
        this.outcome = outcome;
        this.nextActionAt = nextActionAt;
    }

    public long getInteractionId() { return interactionId; }
    public void setInteractionId(long interactionId) { this.interactionId = interactionId; }

    public int getLeadId() { return leadId; }
    public void setLeadId(int leadId) { this.leadId = leadId; }

    public int getAgentId() { return agentId; }
    public void setAgentId(int agentId) { this.agentId = agentId; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getTs() { return ts; }
    public void setTs(Timestamp ts) { this.ts = ts; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public Timestamp getNextActionAt() { return nextActionAt; }
    public void setNextActionAt(Timestamp nextActionAt) { this.nextActionAt = nextActionAt; }
}
