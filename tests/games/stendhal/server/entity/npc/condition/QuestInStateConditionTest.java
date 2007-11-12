package games.stendhal.server.entity.npc.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.entity.player.Player;
import marauroa.common.Log4J;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;
import utilities.SpeakerNPCTestHelper;

public class QuestInStateConditionTest {
	private static final String QUESTNAME = "questname";

	@BeforeClass
	public static void setUpClass() throws Exception {
		Log4J.init();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testFire() {
		String validState = "valid";
		assertFalse(new QuestInStateCondition(QUESTNAME, validState).fire(
				PlayerTestHelper.createPlayer(), "testQuestInStateCondition",
				SpeakerNPCTestHelper.createSpeakerNPC()));
		Player bob = PlayerTestHelper.createPlayer();

		bob.setQuest(QUESTNAME, "valid");
		assertTrue(new QuestInStateCondition(QUESTNAME, validState).fire(bob,
				"testQuestInStateCondition",
				SpeakerNPCTestHelper.createSpeakerNPC()));

		bob.setQuest(QUESTNAME, "");
		assertFalse(new QuestInStateCondition(QUESTNAME, validState).fire(bob,
				"testQuestInStateCondition",
				SpeakerNPCTestHelper.createSpeakerNPC()));

		bob.setQuest(QUESTNAME, null);
		assertFalse(new QuestInStateCondition(QUESTNAME, validState).fire(bob,
				"testQuestInStateCondition",
				SpeakerNPCTestHelper.createSpeakerNPC()));

		bob.setQuest(QUESTNAME, "done");
		assertFalse(new QuestInStateCondition(QUESTNAME, validState).fire(bob,
				"testQuestInStateCondition",
				SpeakerNPCTestHelper.createSpeakerNPC()));

	}

	@Test
	public final void testQuestInStateCondition() {
		new QuestInStateCondition(QUESTNAME, "");
	}

	@Test
	public final void testToString() {
		assertEquals("QuestInState <questname,testToString>",
				new QuestInStateCondition(QUESTNAME, "testToString").toString());
	}

	@Test
	public void testEquals() throws Throwable {
		String state = "state";
		assertFalse(new QuestInStateCondition(QUESTNAME, state).equals(null));

		QuestInStateCondition obj = new QuestInStateCondition(QUESTNAME, state);
		assertTrue(obj.equals(obj));

		assertTrue(new QuestInStateCondition(QUESTNAME, state).equals(new QuestInStateCondition(
				QUESTNAME, state)));
		assertTrue(new QuestInStateCondition(null, state).equals(new QuestInStateCondition(
				null, state)));
		assertTrue(new QuestInStateCondition(QUESTNAME, null).equals(new QuestInStateCondition(
				QUESTNAME, null)));

		assertFalse(new QuestInStateCondition(QUESTNAME, state).equals(new Object()));

		assertFalse(new QuestInStateCondition(null, state).equals(new QuestInStateCondition(
				QUESTNAME, state)));
		assertFalse(new QuestInStateCondition(QUESTNAME, null).equals(new QuestInStateCondition(
				QUESTNAME, state)));
		assertFalse(new QuestInStateCondition(QUESTNAME, null).equals(new QuestInStateCondition(
				null, state)));
		assertFalse(new QuestInStateCondition(QUESTNAME, state).equals(new QuestInStateCondition(
				QUESTNAME, state + "2")));

		assertFalse(new QuestInStateCondition(QUESTNAME, state).equals(new QuestInStateCondition(
				QUESTNAME, state) {
		}));
	}

	@Test
	public void testHashCode() throws Throwable {

		QuestInStateCondition obj = new QuestInStateCondition(QUESTNAME,
				"state");
		assertEquals(obj.hashCode(), obj.hashCode());

		assertEquals(
				new QuestInStateCondition("questname", "state").hashCode(),
				new QuestInStateCondition("questname", "state").hashCode());
		assertEquals(new QuestInStateCondition(null, "state").hashCode(),
				new QuestInStateCondition(null, "state").hashCode());
		assertEquals(new QuestInStateCondition("questname", null).hashCode(),
				new QuestInStateCondition("questname", null).hashCode());

	}

}
