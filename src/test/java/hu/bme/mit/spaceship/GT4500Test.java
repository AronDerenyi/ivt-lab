package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;
  private GT4500 ship;

  @BeforeEach
  public void init(){
	primaryTorpedoStore = mock(TorpedoStore.class);
	secondaryTorpedoStore = mock(TorpedoStore.class);
	ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Twice(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(true);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(true);

	boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
	boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);
	
	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, firstResult && secondResult);
  }

  @Test
  public void fireTorpedo_All(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(true);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_First_Empty(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(true);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, never()).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(true);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, never()).fire(1);
	verify(secondaryTorpedoStore, never()).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_First_Failure(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, never()).fire(1);
    assertEquals(false, result);
  }

  /**
   * From source
   */
  @Test
  public void fireTorpedo_Single_Twice_First_Failure(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
	boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
	assertEquals(false, firstResult);
	assertEquals(true, secondResult);
  }

  // --- For 100% coverage ---

  @Test
  public void fireTorpedo_Single_Twice_Second_Empty(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(true);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

	boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
	boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, times(2)).fire(1);
	verify(secondaryTorpedoStore, never()).fire(1);
    assertEquals(true, firstResult && secondResult);
  }

  @Test
  public void fireTorpedo_Single_Twice_Second_Empty_Then_Both_Empty(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(true);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

	boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);

	when(primaryTorpedoStore.isEmpty()).thenReturn(true);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, never()).fire(1);
    assertEquals(true, firstResult);
	assertEquals(false, secondResult);
  }

  @Test
  public void fireTorpedo_Single_Twice_Second_Failure(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(true);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    boolean firstResult = ship.fireTorpedo(FiringMode.SINGLE);
	boolean secondResult = ship.fireTorpedo(FiringMode.SINGLE);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
	assertEquals(true, firstResult);
	assertEquals(false, secondResult);
  }

  @Test
  public void fireTorpedo_All_First_Failure(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_Second_Failure(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(true);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_Both_Failure(){
	when(primaryTorpedoStore.isEmpty()).thenReturn(false);
	when(primaryTorpedoStore.fire(1)).thenReturn(false);
	when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
	when(secondaryTorpedoStore.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

	verify(primaryTorpedoStore, times(1)).fire(1);
	verify(secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_None(){
    boolean result = ship.fireTorpedo(FiringMode.NONE);
	assertEquals(false, result);
  }

  @Test
  public void fireLazer_Failure(){
    boolean result = ship.fireLaser(FiringMode.SINGLE);
	assertEquals(false, result);
  }
}
