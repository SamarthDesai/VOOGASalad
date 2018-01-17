package authoring_actionconditions;

/**
 * ControllerConditionActionTabs--controls the flow of information between the condition and action tabs. This information contains which actions are 
 * defined in the action tab so that the condition tab can adjust its conditions rows according to this new size
 * Dependencies: depends on the condition and action tabs to get information from 
 * Example of how to use--create an instance of the controller each time either the action or condition tab is updated, and that it is it. It will 
 * control all of the button listeners accordingly
 * @author Owen Smith
 *
 */
public class ControllerConditionActionTabs {
	
	private ConditionTab<ConditionRow> conditionTab;
	private ActionTab<ActionRow> actionTab;
	
	public ControllerConditionActionTabs(ConditionTab<ConditionRow> conditionTab,ActionTab<ActionRow> actionTab) {
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
		this.conditionTab.addButtonListener(e -> addConditionActionRow(this.conditionTab));
		this.actionTab.addButtonListener(e -> {
			addConditionActionRow(this.actionTab);
			addActionOption();
		});
		this.conditionTab.addRemoveListener(e -> removeConditionActionRow(this.conditionTab));
		this.actionTab.addRemoveListener(e -> {
			removeActionOption();
			removeConditionActionRow(this.actionTab);
		});
	}
	
	private void addConditionActionRow(ActionTab actionConditionTab) {
		if(actionConditionTab instanceof ConditionTab<?>) {
			((ConditionTab<ConditionRow>) actionConditionTab).addCondition(actionTab.getCurrentActions());
		}
		else ((ActionTab<ActionRow>) actionConditionTab).addAction();
		actionConditionTab.addRemoveOption();
	}
	
	private void removeConditionActionRow(ActionTab actionConditionTab) {
		if(!(actionConditionTab.getRemoveValue() == null)) {
			int rowToBeRemoved = actionConditionTab.getRemoveValue();
			actionConditionTab.removeActionCondtion(rowToBeRemoved - 1);
			actionConditionTab.removeRowOption(rowToBeRemoved - 1);
		}
	}
	
	private void addActionOption() {
		conditionTab.addActionOption();
	}
	
	private void removeActionOption() {
		if(!(actionTab.getRemoveValue() == null)) {
			conditionTab.removeActionOption(actionTab.getRemoveValue() - 1);
		}
	}

}
