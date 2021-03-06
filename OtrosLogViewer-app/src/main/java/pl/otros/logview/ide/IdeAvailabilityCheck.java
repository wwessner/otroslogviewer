package pl.otros.logview.ide;

import pl.otros.logview.gui.services.jumptocode.JumpToCodeService;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdeAvailabilityCheck implements Runnable {
  private static final Logger LOGGER = Logger.getLogger(IdeAvailabilityCheck.class.getName());
  private static final String ICE_DISCONNECTED_TOOLTIP = "<HTML>OtrosLogViewer can't connect with your IDE.<BR/>Click to configure</HTML>";
  private static final String IDE_CONNECTED_TOOLTIP ="<HTML>OtrosLogViewer is connected with your IDE.<BR/>Click to configure</HTML>";
  private JButton iconLabel;
  private JumpToCodeService jumpToCodeService;
  private volatile Ide lastIde = Ide.IDEA;
  private boolean lastTimeIdeAvailable = false;

  public IdeAvailabilityCheck(JButton iconLabel, JumpToCodeService jumpToCodeService) {
    this.iconLabel = iconLabel;
    this.jumpToCodeService = jumpToCodeService;
  }

  @Override
  public void run() {
    LOGGER.fine("Checking if IDE is available");
    boolean ideAvailable = jumpToCodeService.isIdeAvailable();
    Ide ide = jumpToCodeService.getIde();
    if (ide!=null && !ide.equals(Ide.DISCONNECTED)){
      lastIde=ide;
    } else {
      ide = lastIde;
    }
    if (!lastTimeIdeAvailable && ideAvailable){
      jumpToCodeService.clearLocationCaches();
    }
    lastTimeIdeAvailable = ideAvailable;
    try {

    LOGGER.fine("IDE is available: " + ideAvailable + ", current IDE is: " + ide);
    final Icon icon = ideAvailable?ide.getIconConnected():ide.getIconDiscounted();
    final String toolTip = ideAvailable?IDE_CONNECTED_TOOLTIP: ICE_DISCONNECTED_TOOLTIP;

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        iconLabel.setIcon(icon);
        iconLabel.setToolTipText(toolTip);
      }
    });
    } catch (Exception e){
      LOGGER.log(Level.SEVERE,"Exception when checking IDE",e);
    }

  }
}
