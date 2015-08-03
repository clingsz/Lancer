package mainrun;
import java.awt.image.BufferedImage;

import element.City;
import element.Legion;
import element.Squad;
import element.World;
import element.WorldUnit;
import screen.Battle;
import screen.CityMenu;
import screen.LegionMenu;
import screen.Menu;
import screen.StartMenu;
import screen.Viewer;
import screen.WorldMenu;



public class GameState {
	
	public World world = new World();
	public InputHandler input;
	public Viewer viewer;
	
	public Battle battle = null;
	public Legion legion = null;
			
	int id;
	
	public GameState(InputHandler input2){
		this.input = input2;
	}
			
	public void testBattle(){
		battle = new Battle(this);
		battle.testPlay = true;
	}
	
	public void init(int w, int h){
		RandomNameGenerator.init();
		viewer = new Viewer(w,h,input,this);
		Squad.init();
		setStartMenu();
	}
	
	public void setStartMenu(){
		viewer.setStartMenu();
		state = State.startMenu;
	}
			
	public void resetGame(){
//		world.init(128, 128, input);
		world.init(64, 64, input);
		viewer.setWorld(world);
		state = State.world;
	}
		
	public BufferedImage getImage(){
		return viewer.getImage();
	}
	
	public void render(){
		viewer.render(this);
	}

//	public void tick(){
//		if (state == State.menu){
//			menu.tick();
//		}
//		else if (state == State.legionMove){
//			int mx = 0;
//			int my = 0;
//			if (input.up.clicked) my = -1;
//			if (input.down.clicked) my = 1;
//			if (input.left.clicked) mx = -1;
//			if (input.right.clicked) mx = 1;
//			if (mx!=0 || my!=0){
//				viewer.moveFocus(world,mx,my);
//			}
//			if (input.enter.clicked){
//				viewer.clearSelection();
//				setMenu(new WorldMenu());
//			}
//			if (input.cancel.clicked){
//				setWorldBrowsing();
//			}
//			if (input.next.clicked){
//				world.nextDay();
//				setWorldBrowsing();
//			}
//			if (input.select.clicked){
//				if(viewer.selectTarget()){
//					setWaitChoice();
//				}
//			}
//		}
//		else if (state == State.legionAttack){
//			int mx = 0;
//			int my = 0;
//			if (input.up.clicked) my = -1;
//			if (input.down.clicked) my = 1;
//			if (input.left.clicked) mx = -1;
//			if (input.right.clicked) mx = 1;
//			if (mx!=0 || my!=0){
//				viewer.moveFocus(world,mx,my);
//			}
//			if (input.cancel.clicked){
//				setWorldBrowsing();
//			}
//			if (input.select.clicked){
//				if(viewer.selectAttackTarget(world,legion)){
//					setBattle(viewer.getAttackTarget(world));
//				}
//			}
//		}
//		else if (state == State.waitChoice){
//			if (input.cancel.clicked){
//				moveLegion(legion);
//			}
//			else if (input.select.clicked){
//				legion.moveTo(viewer.targetX, viewer.targetY);
//				this.setWorldBrowsing();
//			}
//		}
//		else if (state == State.worldBrowsing){	
//			int mx = 0;
//			int my = 0;
//			if (input.up.down) my = -1;
//			if (input.down.down) my = 1;
//			if (input.left.down) mx = -1;
//			if (input.right.down) mx = 1;
//			if (input.enter.clicked) this.setMenu(new WorldMenu());
//			viewer.moveFocus(world,mx,my);
//			if (input.select.clicked && viewer.canSelectFocus(world.currentPlayerID)){
//				viewer.selectUnit();
//				viewer.renderWorld(world);
//				if (viewer.selectedUnit instanceof City){
//					setMenu(new CityMenu((City)viewer.selectedUnit));
//				}
//				else{
//					setMenu(new LegionMenu((Legion)viewer.selectedUnit));
//				}
//			}
//			if (input.next.clicked){
//				world.nextDay();
//			}
//		}
//		else if (state==State.battleBrowsing){
//			if (input.up.clicked) battle.prevSelect();
//			if (input.down.clicked) battle.nextSelect();
//			if (battle.selectOrder){
//				if (input.left.clicked) battle.prevSelect();
//				if (input.right.clicked) battle.nextSelect();
//			}
//			else{
//				if (input.left.clicked) battle.nextSelect();
//				if (input.right.clicked) battle.prevSelect();
//			}
//			if (input.tab.clicked) battle.switchSelect();
//			if (input.select.clicked) battle.issueOrder();
//		}
//	}
	
	private void setBattle(WorldUnit attackTarget) {
		state = State.battle;
		if (attackTarget instanceof City){
			City ac = (City)(attackTarget);
			System.out.println(legion.getLegionName()+ " is battling with city " + ac.cityName);
		}
		else{
			Legion al = (Legion)(attackTarget);
			System.out.println(legion.getLegionName()+ " is battling with legion " + al.getLegionName());
		}
		battle = new Battle(this,legion,attackTarget);
	}

	public void endGame(){
		System.exit(0);
	}
	
}
