package net.sf.anathema.hero.magic.advance;

import net.sf.anathema.hero.environment.HeroEnvironment;
import net.sf.anathema.hero.individual.change.ChangeAnnouncer;
import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.hero.magic.advance.costs.GenericCostAnalyzer;
import net.sf.anathema.hero.magic.advance.creation.DefaultMagicModel;
import net.sf.anathema.hero.magic.advance.creation.FavoredMagicModel;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationCostCalculator;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationCostEvaluator;
import net.sf.anathema.hero.magic.advance.creation.MagicCreationData;
import net.sf.anathema.hero.magic.advance.experience.MagicExperienceData;
import net.sf.anathema.hero.magic.model.favored.CheapenedChecker;
import net.sf.anathema.hero.magic.model.favored.IsCheapenedMagic;
import net.sf.anathema.hero.magic.model.learn.MagicLearner;
import net.sf.anathema.hero.magic.template.advance.MagicPointsTemplate;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.magic.data.Magic;
import net.sf.anathema.points.model.PointModelFetcher;
import net.sf.anathema.points.model.overview.SpendingModel;
import net.sf.anathema.points.model.overview.WeightedCategory;

public class MagicPointsModel implements HeroModel
{
	public static final SimpleIdentifier ID = new SimpleIdentifier ("MagicPoints");
	private final MagicPointsTemplate template;
	private final MagicCreationCostEvaluator creationEvaluator = new MagicCreationCostEvaluator ();
	private final IsCheapenedMagic isCheapenedMagic = new IsCheapenedMagic ();
	
	public MagicPointsModel (MagicPointsTemplate template)
	{
		this.template = template;
	}
	
	@Override
	public Identifier getId ()
	{
		return ID;
	}
	
	@Override
	public void initialize (HeroEnvironment environment, Hero hero)
	{
		initializeBonusPoints (hero);
	}
	
	@Override
	public void initializeListening (ChangeAnnouncer announcer)
	{
		// nothing to do, until bonus points are created the way, they should be
	}
	
	public MagicExperienceData getExperienceCost ()
	{
		return new MagicExperienceData (template);
	}
	
	private MagicCreationData getMagicCreationData ()
	{
		return new MagicCreationData (template);
	}
	
	private void initializeBonusPoints (Hero hero)
	{
		initCreation (hero);
	}
	
	private void initCreation (Hero hero)
	{
		MagicCreationCostCalculator calculator = createBonusCalculator (hero);
		initBonusCalculation (hero, calculator);
		if (getMagicCreationData ().getGeneralMagicPicks () > 0)
		{
			initBonusOverview (hero, calculator);
		}
	}
	
	private void initBonusCalculation (Hero hero, MagicCreationCostCalculator calculator)
	{
		PointModelFetcher.fetch (hero).addBonusPointCalculator (calculator);
	}
	
	private void initBonusOverview (Hero hero, MagicCreationCostCalculator calculator)
	{
		PointModelFetcher.fetch (hero).addBonusCategory (new WeightedCategory (400, "Magic"));
		PointModelFetcher.fetch (hero).addToBonusOverview (new DefaultMagicModel (calculator, getMagicCreationData ()));
		SpendingModel favoredMagicModel = new FavoredMagicModel (calculator, getMagicCreationData ());
		if (favoredMagicModel.getAllotment () > 0)
		{
			PointModelFetcher.fetch (hero).addToBonusOverview (favoredMagicModel);
		}
	}
	
	private MagicCreationCostCalculator createBonusCalculator (Hero hero)
	{
		return new MagicCreationCostCalculator (creationEvaluator, getMagicCreationData (), new GenericCostAnalyzer (hero));
	}
	
	public void registerMagicLearner (MagicLearner learner)
	{
		creationEvaluator.registerMagicLearner (learner);
	}
	
	public void addCheapenedChecker (CheapenedChecker checker)
	{
		isCheapenedMagic.add (checker);
	}
	
	public boolean isMagicCheapened (Magic magic)
	{
		return isCheapenedMagic.isFavored (magic);
	}
}
