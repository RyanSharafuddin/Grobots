/*******************************************************************************
 * Copyright (c) 2002-2013 (c) Devon and Warren Schudy
 * Copyright (c) 2014  Devon and Warren Schudy, Mike Anderson
 *******************************************************************************/
//RobotType.cpp
//Grobots (c) 2002-2004 Devon and Warren Schudy
//Distributed under the GNU General Public License.

package sides;

import java.awt.Color;
import java.awt.image.BufferedImage;

import simulation.GBProjection;
import simulation.GBRobot;
import support.FinePoint;
import brains.Brain;
import brains.BrainSpec;

public class RobotType implements GBProjection {
	static int iconHeight = 64;
	public static final double kStandardMass = 20;
	public boolean debug = true;
	public Side side;
	public String name;
	public int id;
	public Color color;
	public GBRobotDecoration decoration = GBRobotDecoration.none;
	public Color decorationColor;
	public HardwareSpec hardware;
	public BrainSpec brain;
	public int population;
	public double biomass;
	public BufferedImage icon;
	// private:
	double cost;
	double mass;

	public RobotType(Side owner) {
		side = owner;
		debug = owner.debug;
		decoration = GBRobotDecoration.none;
		decorationColor = Color.black;
		color = Color.black;
		hardware = new HardwareSpec(debug);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RobotType))
			return false;
		RobotType other = (RobotType) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (side == null) {
			if (other.side != null)
				return false;
		} else if (!side.equals(other.side))
			return false;
		return true;
	}

	// @Override
	public boolean _equals(Object other) {
		if (this == other)
			return true;
		if (other == null || !(other instanceof RobotType))
			return false;
		return this.side.name.equals(((RobotType) other).side.name)
				&& this.name.equals(((RobotType) other).name);
	}

	public void ResetSampledStatistics() {
		population = 0;
		biomass = 0;
	}

	public void ReportRobot(double botBiomass) {
		population++;
		biomass += botBiomass;
	}

	public void SetName(String newname) {
		name = newname;
	}

	@Override
	public String toString() {
		return side.Name() + (side.Name().endsWith("s") ? "'" : "'s") + " "
				+ name;
	}

	public int ID() {
		return id;
	}

	public void SetID(int newid) {
		id = newid;
	}

	public Color Color() {
		return color;
	}

	void SetColor(Color newcolor) {
		color = newcolor;
	}

	public GBRobotDecoration Decoration() {
		return decoration;
	}

	public Color DecorationColor() {
		return decorationColor;
	}

	void SetDecoration(GBRobotDecoration dec, Color col) {
		decoration = dec;
		decorationColor = col;
	}

	public HardwareSpec Hardware() {
		return hardware;
	}

	public BrainSpec Brain() {
		return brain;
	}

	public void SetBrain(BrainSpec spec) {
		spec.Check();
		brain = spec;
	}

	public Brain MakeBrain() {
		if (brain == null) {
			return null;
		}
		return brain.MakeBrain();
	}

	public void Recalculate() {
		hardware.Recalculate();
		cost = hardware.Cost() + (brain != null ? brain.Cost() : 0);
		mass = hardware.Mass() + (brain != null ? brain.Mass() : 0);
	}

	public double Cost() {
		return cost;
	}

	public double Mass() {
		return mass;
	}

	public double MassiveDamageMultiplier(double mass) {
		double multiplier = 1;
		if (mass > kStandardMass)
			multiplier += (mass - kStandardMass) / 50;
		return multiplier;
	}

	public void makeIcon() {
		icon = new BufferedImage(iconHeight, iconHeight, BufferedImage.TYPE_INT_ARGB);
		GBRobot bot = new GBRobot(this, new FinePoint(fromScreenX(iconHeight / 2), fromScreenY(iconHeight / 2)));
		bot.setReloaded();
		icon = bot.drawIn(iconHeight / 2, true);
	}

	@Override
	public int toScreenX(double x) {
		return (int)Math.round(x * getScale());
	}

	@Override
	public int toScreenY(double y) {
		return (int)Math.round(iconHeight - y * getScale());
	}

	@Override
	public double fromScreenX(int h) {
		return (iconHeight - (double)h) / getScale();
	}

	@Override
	public double fromScreenY(int v) {
		return (iconHeight - (double)(iconHeight - v)) / getScale();
	}

	@Override
	public int getScale() {
		return iconHeight / 2;
	}
}
