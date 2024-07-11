import 'reflect-metadata';
import { injectable } from 'inversify';
import { AbstractAction, ActionExecutionContext } from '@libreforge/libreforge-framework';

const ARG_URL = "url";

@injectable()
export class RNRouteToPageAction extends AbstractAction {

  name = 'RNRouteToPage';

  getName() {
    return this.name;
  }

  async execute(context: ActionExecutionContext): Promise<{ next: boolean, result: any }> {
    
    const { args, router } = context;

    const url = args[ARG_URL];
    if (!url) {
      console.error(`${this.name} > ${ARG_URL} argument not provided`);
      return { next: false, result: undefined };
    }

    const targetUrl = url.replace('/', '');
    router.navigate(targetUrl);

    return { next: true, result: undefined };
  }

  override getArgsDefinition(): { name: string; type: string; label: string }[] {
    return [
    ];
  };  
}
