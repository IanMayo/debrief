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
package ASSET.Util.XML.Decisions.Tactical;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import ASSET.Models.Decision.Tactical.Investigate;
import ASSET.Models.Decision.TargetType;
import ASSET.Models.Detection.DetectionEvent;
import ASSET.Util.XML.Decisions.Util.TargetTypeHandler;
import MWC.GenericData.WorldDistance;

abstract public class InvestigateHandler extends CoreDecisionHandler
{

  private final static String type = "Investigate";

  private final static String TARGET_TYPE = "TargetType";
  private final static String WATCH_TYPE = "WatchType";
  private final static String DETECTION_LEVEL = "DetectionLevel";
  private final static String INVESTIGATE_HEIGHT = "Height";
  private final static String COLLAB_SEARCH = "CollaborativeSearch";


  TargetType _myTargetType;
  String _detLevel = null;
  WorldDistance _investigateHeight;

	protected TargetType _myWatchType;

	protected Boolean _collabSearch;

  /**
   * get the handler ready
   */
  private static DetectionEvent.DetectionStatePropertyEditor _detectionHandler = new DetectionEvent.DetectionStatePropertyEditor();


  public InvestigateHandler()
  {
    super(type);

    addAttributeHandler(new HandleAttribute(DETECTION_LEVEL)
    {
      public void setValue(String name, String value)
      {
        _detLevel = value;
      }
    });
    addAttributeHandler(new HandleBooleanAttribute(COLLAB_SEARCH)
    {
      public void setValue(String name, boolean value)
      {
        _collabSearch = new Boolean(value);
      }
    });

    addHandler(new ASSET.Util.XML.Decisions.Util.TargetTypeHandler(TARGET_TYPE)
    {
      public void setTargetType(final TargetType type)
      {
        _myTargetType = type;
      }
    });
    addHandler(new ASSET.Util.XML.Decisions.Util.TargetTypeHandler(WATCH_TYPE)
    {
      public void setTargetType(final TargetType type)
      {
        _myWatchType = type;
      }
    });

    addHandler(new MWC.Utilities.ReaderWriter.XML.Util.WorldDistanceHandler(INVESTIGATE_HEIGHT)
    {
      public void setWorldDistance(WorldDistance res)
      {
        _investigateHeight = res;
      }
    });
  }


  public void elementClosed()
  {
    // find out the det level
    _detectionHandler.setAsText(_detLevel);

    int val = _detectionHandler.getIndex();

    final Investigate tr = new Investigate(null, _myTargetType, _myWatchType, val, _investigateHeight);

    if(_collabSearch != null)
    	tr.setCollaborativeSearch(_collabSearch.booleanValue());
    
    // update the parent fields
    super.setAttributes(tr);

    setModel(tr);

    _myTargetType = null;
    _myWatchType  = null;
    _detLevel = null;
    _investigateHeight = null;
    _collabSearch = null;

  }


  abstract public void setModel(ASSET.Models.DecisionType dec);

  static public void exportThis(final Object toExport, final org.w3c.dom.Element parent,
                                final org.w3c.dom.Document doc)
  {
    // create ourselves
    final org.w3c.dom.Element element = doc.createElement(type);

    // get data item
    final Investigate bb = (Investigate) toExport;

    _detectionHandler.setIndex(bb.getDetectionLevel());


    // output the parent fields
    CoreDecisionHandler.exportThis(bb, element, doc);

    // output it's attributes
    TargetTypeHandler.exportThis(TARGET_TYPE, bb.getTargetType(), element, doc);
    if(bb.getWatchType() != null)
    TargetTypeHandler.exportThis(WATCH_TYPE, bb.getWatchType(), element, doc);
    element.setAttribute(DETECTION_LEVEL, _detectionHandler.getAsText());
    element.setAttribute(COLLAB_SEARCH,writeThis(bb.isCollaborativeSearch()));

    if (bb.getInvestigateHeight() != null)
    {
      MWC.Utilities.ReaderWriter.XML.Util.WorldDistanceHandler.exportDistance(INVESTIGATE_HEIGHT, bb.getInvestigateHeight(), element, doc);
    }

    parent.appendChild(element);
  }


}