package com.aihello.amazon.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Suggestions implements Serializable {

	private static final long serialVersionUID = 1L;
	String suggType;
	String type;
	String value;
	String refTag;
	String candidateSources;
	String strategyId;
	String prior;
	String ghost;
	String help;
	String fallback;
	String blackListed;
	String spellCorrected;
	String xcatOnly;
	String totalCount;

	public String getSuggType() {
		return suggType;
	}

	public void setSuggType(String suggType) {
		this.suggType = suggType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRefTag() {
		return refTag;
	}

	public void setRefTag(String refTag) {
		this.refTag = refTag;
	}

	public String getCandidateSources() {
		return candidateSources;
	}

	public void setCandidateSources(String candidateSources) {
		this.candidateSources = candidateSources;
	}

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getPrior() {
		return prior;
	}

	public void setPrior(String prior) {
		this.prior = prior;
	}

	public String getGhost() {
		return ghost;
	}

	public void setGhost(String ghost) {
		this.ghost = ghost;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getFallback() {
		return fallback;
	}

	public void setFallback(String fallback) {
		this.fallback = fallback;
	}

	public String getBlackListed() {
		return blackListed;
	}

	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}

	public String getSpellCorrected() {
		return spellCorrected;
	}

	public void setSpellCorrected(String spellCorrected) {
		this.spellCorrected = spellCorrected;
	}

	public String getXcatOnly() {
		return xcatOnly;
	}

	public void setXcatOnly(String xcatOnly) {
		this.xcatOnly = xcatOnly;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Suggestions [suggType=" + suggType + ", type=" + type + ", value=" + value + ", refTag=" + refTag
				+ ", candidateSources=" + candidateSources + ", strategyId=" + strategyId + ", prior=" + prior
				+ ", ghost=" + ghost + ", help=" + help + ", fallback=" + fallback + ", blackListed=" + blackListed
				+ ", spellCorrected=" + spellCorrected + ", xcatOnly=" + xcatOnly + ", totalCount=" + totalCount + "]";
	}

}
