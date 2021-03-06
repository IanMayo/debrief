/*
 *    Debrief - the Open Source Maritime Analysis Application
 *    http://debrief.info
 *
 *    (C) 2000-2014, PlanetMayo Ltd
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the Eclipse Public License v1.0
 *    (http://www.eclipse.org/legal/epl-v10.html)
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 */
package ASSET.Models.Vessels;

import ASSET.Models.Movement.SSKMovement;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class SSK extends SSN {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** the Height we snort at
   *
   */
  static final public double CHARGE_HEIGHT = -15;

  public SSK(final int id) {
    this(id, null,null,null);
  }

  public SSK(final int id,
             final ASSET.Participants.Status status,
             final ASSET.Participants.DemandedStatus demStatus,
             final String name)
  {
    super(id, status, demStatus, name);

    super.setMovementModel(new ASSET.Models.Movement.SSKMovement());
  }

  public void setChargeRate(double val)
  {
    SSKMovement theMovement = (SSKMovement)super.getMovementModel();
    theMovement.setChargeRate(val);
  }

  public double getChargeRate()
  {
    SSKMovement theMovement = (SSKMovement)super.getMovementModel();
    return theMovement.getChargeRate();
  }


}