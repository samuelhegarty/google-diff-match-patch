package com.googlecode.diffmatchpatch.core;

/**
 * Created by shegarty on 13/01/2016.
 */
public class DiffSettings{
	private float diffTimeout = 1.0f;
	/**
	 * Cost of an empty edit operation in terms of edit characters.
	 */
	private short diffEditCost = 4;
	/**
	 * At what point is no match declared (0.0 = perfection, 1.0 = very loose).
	 */
	private float matchThreshold = 0.5f;
	/**
	 * How far to search for a match (0 = exact location, 1000+ = broad match).
	 * A match this many characters away from the expected location will add
	 * 1.0 to the score (0.0 is a perfect match).
	 */
	private int matchDistance = 1000;
	/**
	 * When deleting a large block of text (over ~64 characters), how close do
	 * the contents have to be to match the expected contents. (0.0 = perfection,
	 * 1.0 = very loose).  Note that matchThreshold controls how closely the
	 * end points of a delete need to match.
	 */
	private float patchDeleteThreshold = 0.5f;

	/**
	 * Chunk size for context length.
	 */
	private short patchMargin = 4;

	public short getPatchMargin(){
		return patchMargin;
	}

	public DiffSettings setPatchMargin(short patchMargin){
		this.patchMargin = patchMargin;
		return this;
	}

	public float getPatchDeleteThreshold(){
		return patchDeleteThreshold;
	}

	public DiffSettings setPatchDeleteThreshold(float patchDeleteThreshold){
		this.patchDeleteThreshold = patchDeleteThreshold;
		return this;
	}

	public int getMatchDistance(){
		return matchDistance;
	}

	public DiffSettings setMatchDistance(int matchDistance){
		this.matchDistance = matchDistance;
		return this;
	}

	public float getMatchThreshold(){
		return matchThreshold;
	}

	public DiffSettings setMatchThreshold(float matchThreshold){
		this.matchThreshold = matchThreshold;
		return this;
	}

	public short getDiffEditCost(){
		return diffEditCost;
	}

	public DiffSettings setDiffEditCost(short diffEditCost){
		this.diffEditCost = diffEditCost;
		return this;
	}

	public float getDiffTimeout(){
		return diffTimeout;
	}

	public DiffSettings setDiffTimeout(float diffTimeout){
		this.diffTimeout = diffTimeout;
		return this;
	}
}
