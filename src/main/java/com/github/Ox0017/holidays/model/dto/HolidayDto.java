package com.github.Ox0017.holidays.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.Ox0017.holidays.model.dto.deserialize.LocalDateDeserializer;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayDto {

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonProperty("date")
	private LocalDate date;

	@JsonProperty("fname")
	private String name;

	@JsonProperty("all_states")
	private String allStates;

	@JsonProperty("bw")
	private String bw;

	@JsonProperty("by")
	private String by;

	@JsonProperty("be")
	private String be;

	@JsonProperty("bb")
	private String bb;

	@JsonProperty("hb")
	private String hb;

	@JsonProperty("hh")
	private String hh;

	@JsonProperty("he")
	private String he;

	@JsonProperty("mv")
	private String mv;

	@JsonProperty("ni")
	private String ni;

	@JsonProperty("nw")
	private String nw;

	@JsonProperty("rp")
	private String rp;

	@JsonProperty("sl")
	private String sl;

	@JsonProperty("sn")
	private String sn;

	@JsonProperty("st")
	private String st;

	@JsonProperty("sh")
	private String sh;

	@JsonProperty("th")
	private String th;

	@JsonProperty("comment")
	private String comment;

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAllStates() {
		return this.allStates;
	}

	public void setAllStates(final String allStates) {
		this.allStates = allStates;
	}

	public boolean isAllStates() {
		return isTrue(this.allStates);
	}

	public String getBw() {
		return this.bw;
	}

	public void setBw(final String bw) {
		this.bw = bw;
	}

	public boolean isBw() {
		return isTrue(this.bw);
	}

	public String getBy() {
		return this.by;
	}

	public void setBy(final String by) {
		this.by = by;
	}

	public boolean isBy() {
		return isTrue(this.by);
	}

	public String getBe() {
		return this.be;
	}

	public void setBe(final String be) {
		this.be = be;
	}

	public boolean isBe() {
		return isTrue(this.be);
	}

	public String getBb() {
		return this.bb;
	}

	public void setBb(final String bb) {
		this.bb = bb;
	}

	public boolean isBb() {
		return isTrue(this.bb);
	}

	public String getHb() {
		return this.hb;
	}

	public void setHb(final String hb) {
		this.hb = hb;
	}

	public boolean isHb() {
		return isTrue(this.hb);
	}

	public String getHh() {
		return this.hh;
	}

	public void setHh(final String hh) {
		this.hh = hh;
	}

	public boolean isHh() {
		return isTrue(this.hh);
	}

	public String getHe() {
		return this.he;
	}

	public void setHe(final String he) {
		this.he = he;
	}

	public boolean isHe() {
		return isTrue(this.he);
	}

	public String getMv() {
		return this.mv;
	}

	public void setMv(final String mv) {
		this.mv = mv;
	}

	public boolean isMv() {
		return isTrue(this.mv);
	}

	public String getNi() {
		return this.ni;
	}

	public void setNi(final String ni) {
		this.ni = ni;
	}

	public boolean isNi() {
		return isTrue(this.ni);
	}

	public String getNw() {
		return this.nw;
	}

	public void setNw(final String nw) {
		this.nw = nw;
	}

	public boolean isNw() {
		return isTrue(this.nw);
	}

	public String getRp() {
		return this.rp;
	}

	public void setRp(final String rp) {
		this.rp = rp;
	}

	public boolean isRp() {
		return isTrue(this.rp);
	}

	public String getSl() {
		return this.sl;
	}

	public void setSl(final String sl) {
		this.sl = sl;
	}

	public boolean isSl() {
		return isTrue(this.sl);
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(final String sn) {
		this.sn = sn;
	}

	public boolean isSn() {
		return isTrue(this.sn);
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(final String st) {
		this.st = st;
	}

	public boolean isSt() {
		return isTrue(this.st);
	}

	public String getSh() {
		return this.sh;
	}

	public void setSh(final String sh) {
		this.sh = sh;
	}

	public boolean isSh() {
		return isTrue(this.sh);
	}

	public String getTh() {
		return this.th;
	}

	public void setTh(final String th) {
		this.th = th;
	}

	public boolean isTh() {
		return isTrue(this.th);
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("HolidayDto [");
		sb.append("date=").append(this.date);
		sb.append(", name='").append(this.name).append('\'');
		sb.append(", allStates='").append(this.allStates).append('\'');
		sb.append(", bw='").append(this.bw).append('\'');
		sb.append(", by='").append(this.by).append('\'');
		sb.append(", be='").append(this.be).append('\'');
		sb.append(", bb='").append(this.bb).append('\'');
		sb.append(", hb='").append(this.hb).append('\'');
		sb.append(", hh='").append(this.hh).append('\'');
		sb.append(", he='").append(this.he).append('\'');
		sb.append(", mv='").append(this.mv).append('\'');
		sb.append(", ni='").append(this.ni).append('\'');
		sb.append(", nw='").append(this.nw).append('\'');
		sb.append(", rp='").append(this.rp).append('\'');
		sb.append(", sl='").append(this.sl).append('\'');
		sb.append(", sn='").append(this.sn).append('\'');
		sb.append(", st='").append(this.st).append('\'');
		sb.append(", sh='").append(this.sh).append('\'');
		sb.append(", th='").append(this.th).append('\'');
		sb.append(", comment='").append(this.comment).append('\'');
		sb.append(']');
		return sb.toString();
	}

	private static boolean isTrue(final String value) {
		return "1".equals(value);
	}

}
