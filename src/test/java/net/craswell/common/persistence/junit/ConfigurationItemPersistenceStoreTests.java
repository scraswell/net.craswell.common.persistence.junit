package net.craswell.common.persistence.junit;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.craswell.common.models.ConfigurationItem;
import net.craswell.common.persistence.ConfigurationItemPersistenceStore;

/**
 * Tests for the configuration item persistence store.
 * 
 * @author scraswell@gmail.com
 *
 */
public class ConfigurationItemPersistenceStoreTests {

  /**
   * Tests that we can perform standard CRUD operations using the store.
   * 
   * @throws Exception
   */
  @Test
  public void CanPerformStandardCrudOperations()
      throws Exception {
    ConfigurationItem c = new ConfigurationItem();
    c.setName("Test");
    c.setValue("Value");

    try (ConfigurationItemPersistenceStore cips = new ConfigurationItemPersistenceStore()) {
      cips.Create(c);

      ConfigurationItem d = cips.Read(c.getId());

      Assert.assertTrue(c.getId().equals(d.getId()));

      List<ConfigurationItem> configurationItemList = cips.List();

      for(ConfigurationItem i : configurationItemList) {
        cips.Delete(i);
      }
    }
  }

}
