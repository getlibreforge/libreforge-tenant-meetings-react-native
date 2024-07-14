import { ReactElement, forwardRef, memo, useContext, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IComponents, IPages } from '@libreforge/libreforge-framework-shared';
import { ActionExecutionContext, InversifyContainerProviderContext, getCurrentPageState, getSharedState, 
  ActionVariableEvaluationService, SYMBOL_ACTION_VARIABLE_EVAL_SERVICE, useActions, useDispatch } from '@libreforge/libreforge-framework';
import { useNavigation } from '@react-navigation/native';

const AutomaticActionExecutorComponent = forwardRef((props: { componentId: string, 
  designMode: boolean, pageComponents: IComponents, pages: IPages,
  wrapperComponent?: ReactElement, wrapperContainer?: ReactElement }, ref) => {

  const component = props.pageComponents[props.componentId];
  const isJustOnce = component.props.isJustOnce;

  const [counter, setCounter] = useState(0);

  const dispatch = useDispatch();
  const router = useNavigation();
  // const snackbar = useSnackbar();
  const currentPageState = useSelector(getCurrentPageState);
  const sharedState = useSelector(getSharedState);
  const container = useContext(InversifyContainerProviderContext);
  const variableEvalService = container.get<ActionVariableEvaluationService>(SYMBOL_ACTION_VARIABLE_EVAL_SERVICE);

  const actionGroup = props.pageComponents[props.componentId].actionGroup;
  const actions = useActions(actionGroup, props);

  useEffect(() => {
    let intervalId: any = undefined;

    if (true === props.designMode) {
      /* Do nothing */
      
    } else if (actions.length > 0) {
  
      const callback = async () => {
        console.log('> Callback function called');

        let lastActionResult = { next: true, result: undefined }
        for (let i=0; i<actions.length; i++) {
          const item = actions[i];
          if (!!item.action && true === lastActionResult.next) {

            const actionExecutionContext: ActionExecutionContext = {
              componentId: props.componentId, args: item.args, pageComponents: props.pageComponents, 
              currentPageState, sharedState, dispatch, snackbar: undefined, router, container, collectionRefIdx: undefined, 
              prevExecutionState: lastActionResult.result, variableEvalService
            }

            try {  
              lastActionResult = await item.action.execute(actionExecutionContext);
            } catch (error) {
              console.log(error);
              return;
            }
          }           
        }
      }
  
      if (isJustOnce === true) {
        console.log('Just one time callback execution');
        callback();

      } else /* Scheduled */ {
        console.log('Interval-based callback execution');
        callback();

        const scheduledSeconds = parseInt(component.props.seconds);        
        intervalId = setInterval(() => { 
          callback(); 
        }, scheduledSeconds * 1000);        
      }
  
    } else {
      console.warn(`No action defined for AutomaticActionComponent [id = ${props.componentId}]`);
    }
    
    return () => {
      if (!!intervalId) {
        clearInterval(intervalId);
      }
    }
  }, [counter, props.designMode])
  
  return <></>;
});

export default memo(AutomaticActionExecutorComponent);
