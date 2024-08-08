import { InversifyContainerProviderContext, cleanupCustomComponentProps, useActionHandlers, usePropsOverrideByComponentRef, 
  getCurrentPageState, getExpressionVariableNames, replaceVariable, 
  ActionVariableEvaluationService, SYMBOL_ACTION_VARIABLE_EVAL_SERVICE } from '@libreforge/libreforge-framework';
import { useNavigation } from '@react-navigation/native';
import { forwardRef, useContext } from 'react';
import { StyleSheet, Text } from 'react-native';
import { useSelector } from 'react-redux';
import { IComponents, IPages } from '@libreforge/libreforge-framework-shared';

const getStyles = (props: any) => StyleSheet.create({
  text: {
    fontSize: props.fontSize || 14,
    // lineHeight: props.lineHeight || 21,
    // fontWeight: props.fontWeight || 'bold',
    letterSpacing: props.letterSpacing || 0.25,
    color: props.color || 'white',
    ...cleanupCustomComponentProps(props, { key: 'key' })
  },
});

const VariableTextComponent = forwardRef((props: { componentId: string, pages: IPages, designMode: boolean, overridenComponentPageState: any,
  pageComponents: IComponents, componentPage: string, collectionRefIdx: number | undefined, children: string, }, ref) => {
  
  const container = useContext(InversifyContainerProviderContext);
  const variableEvalService = container.get<ActionVariableEvaluationService>(SYMBOL_ACTION_VARIABLE_EVAL_SERVICE);

  const navigation = useNavigation();
  const styles = getStyles(props);

  let targetProps: any = props;

  const actionGroup = props.pageComponents[props.componentId].actionGroup;
  targetProps = useActionHandlers(targetProps, actionGroup, navigation, undefined);  
  targetProps = usePropsOverrideByComponentRef(props.componentId, targetProps, props.designMode);
      
  let currentPageState = useSelector(getCurrentPageState);
  /* Override page state in case ${overridenComponentPageState} is provided.
   * This approach used in forEach component to narrow scope to iterating ${row}
   */
  if (!!props.overridenComponentPageState) {
    currentPageState = props.overridenComponentPageState;
  }    

  const context = { currentPageState, variableEvalService }  
  const variables = getExpressionVariableNames(props.children);

  // @ts-ignore
  const targetText = replaceVariable(props.children, variables, context);

  // const elementProps = cleanupCustomComponentProps(props);

  return (
    <Text style={styles.text} onPress={() => targetProps.onClick(undefined)}>
      {targetText}
    </Text>
  );
});

export default VariableTextComponent;
