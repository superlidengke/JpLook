package com.pickshell.jplook.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pickshell.jplook.domain.Word;
import com.pickshell.jplook.util.HibernateUtil;
import com.pickshell.jplook.util.StringUtil;

public class WordDaoImpl implements WordDao {
	private Logger logger = LoggerFactory.getLogger(WordDaoImpl.class);

	public boolean save(Word word) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			String name = word.getName();
			if (StringUtil.isBlank(name, word.getMeaning())) {
				throw new RuntimeException(
						"word name and meaning must not null!");
			}

			if (!isExist(session, word)) {
				session.save(word);
				result = true;
			}
			tx.commit();
			logger.debug("save word:" + word);
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 事务中不能再套事务，所以其它方法中不能直接调用isExist(Word word)，因此这个方法中用户传来的session,不开新事务
	 * 
	 * @param session
	 * @param word
	 * @return
	 */
	private boolean isExist(Session session, Word word) {
		boolean result = false;
		String name = word.getName();
		if (StringUtil.isBlank(name, word.getMeaning())) {
			throw new RuntimeException("word name must not null!");
		}
		String sql = "select count(*) from Word where name = ?";
		boolean pronull = StringUtil.isBlank(word.getPronounce());
		if (pronull) {
			sql += " and pronounce is null";
		} else {
			sql += " and pronounce= ?";
		}

		Query query = session.createQuery(sql);
		query.setString(0, word.getName());
		if (!pronull) {
			query.setString(1, word.getPronounce());
		}
		Long total = (Long) query.iterate().next();
		if (total > 0) {
			result = true;
		}
		return result;
	}

	public boolean isExist(Word word) {
		boolean result = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			String name = word.getName();
			if (StringUtil.isBlank(name, word.getMeaning())) {
				throw new RuntimeException("word name must not null!");
			}
			String sql = "select count(*) from Word where name = ?";
			boolean pronull = StringUtil.isBlank(word.getPronounce());
			if (pronull) {
				sql += " and pronounce is null";
			} else {
				sql += " and pronounce= ?";
			}

			Query query = session.createQuery(sql);
			query.setString(0, word.getName());
			if (!pronull) {
				query.setString(1, word.getPronounce());
			}
			Long total = (Long) query.iterate().next();
			if (total > 0) {
				result = true;
			}
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return result;
	}

	public boolean update(Word word) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(word);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return false;
	}

	public List<Word> fetchWords(int offset, int limit) {
		List<Word> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("from Word");
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			list = query.list();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return list;
	}

	public long getTotalNumber() {
		long total = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("select count(*) from Word");
			total = (Long) query.iterate().next();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return total;
	}

	public List<Word> getWord(String name) {
		List<Word> words = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session
					.createQuery("from Word where name = ? or pronounce= ?");
			query.setString(0, name);
			query.setString(1, name);
			query.setCacheable(true);
			words = query.list();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return words;
	}

	public List<Word> fetchWordsById(int minId, int maxId) {
		List<Word> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session
					.createQuery("from Word where id between ? and ?");
			query.setInteger(0, minId);
			query.setInteger(1, maxId);
			list = query.list();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return list;
	}

	public Word getWordsById(int id) {
		Word word = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("from Word where id = ?");
			query.setInteger(0, id);
			word = (Word) query.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return word;
	}

	public Word get(int id) {
		Word word = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			word = (Word) session.get(Word.class, id);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return word;
	}

	public int getMaxId() {
		int maxId = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery("select max(id) from Word");
			maxId = (Integer) query.iterate().next();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return maxId;
	}

	public List<String> fetchSimilarWords(String name, int limit) {
		List<String> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			// 先将完全匹配的加入列表，再将前端匹配的加入列表
			Query query = session
					.createQuery("select DISTINCT name from Word where name like ?");
			query.setString(0, name);
			query.setMaxResults(limit);
			list = query.list();

			// Query query = session
			// .createQuery("select DISTINCT name from Word where name like ?");
			query.setString(0, name + '%');
			query.setMaxResults(limit);
			if (list == null) {
				list = query.list();
			} else {
				list.addAll(query.list());
			}

			/*
			 * 目前name属性不为空，没有汉字的词，name显示假名
			 */
			if (list.size() < 5) {
				query = session
						.createQuery("select DISTINCT pronounce from Word where pronounce like ?");
				query.setString(0, name + '%');
				query.setMaxResults(limit - list.size());
				List<String> list2 = query.list();

				list.addAll(list2);
			}

			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return list;
	}

	@Override
	public boolean deleteById(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE from Word WHERE id = ?";
			Query q = session.createQuery(hql);
			q.setInteger(0, id);
			// 执行更新语句
			int num = q.executeUpdate();
			tx.commit();
			if (num > 0)
				return true;
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return false;
	}
}
