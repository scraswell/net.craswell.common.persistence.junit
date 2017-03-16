package net.craswell.common.persistence.junit;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.Assert;

import net.craswell.common.models.ConfigurationItem;
import net.craswell.common.persistence.SqLiteSessionManagerImpl;

/**
 * SqLite session manager tests.
 * 
 * @author scraswell@gmail.com
 *
 */
public class SqLiteSessionManagerImplTests {

  /**
   * Tests that the session manager can persist, retrieve and delete objects.
   */
  @Test
  public void CanPerformStandardCrudOperations() {
    try (
        SqLiteSessionManagerImpl sm = new SqLiteSessionManagerImpl();
        Session s = sm.openSession()) {
      Transaction tx = s.beginTransaction();
      ConfigurationItem c = new ConfigurationItem();

      c.setName("Test");
      c.setValue("Value");

      s.saveOrUpdate(c);
      tx.commit();

      // Create CriteriaBuilder
      CriteriaBuilder builder = s.getCriteriaBuilder();

      // Create CriteriaQuery
      CriteriaQuery<ConfigurationItem> criteria = builder
          .createQuery(ConfigurationItem.class);
      Root<ConfigurationItem> variableRoot = criteria
          .from(ConfigurationItem.class);
      criteria.select(variableRoot);

      List<ConfigurationItem> configurationItemList = s.createQuery(criteria)
          .list();

      Assert.assertTrue(configurationItemList.size() > 0);

      tx = s.beginTransaction();
      for(ConfigurationItem i : configurationItemList) {
        s.delete(i);
      }
      tx.commit();

      configurationItemList = s.createQuery(criteria)
          .list();

      Assert.assertTrue(configurationItemList.size() == 0);
    }
  }
}
