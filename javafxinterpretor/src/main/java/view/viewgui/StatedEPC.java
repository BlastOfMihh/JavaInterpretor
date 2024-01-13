package view.viewgui;

public abstract class StatedEPC
{
    public enum States{ IDLE, EXECUTING, FINISHED, NONE }
    States currentState=States.IDLE;

    protected abstract void enterIdle();
    protected abstract void enterExecuting();
    protected void enterFinished(){}

    public States checkValidTransition(States oldState, States newState){
        if (oldState==States.IDLE && newState==States.EXECUTING)
            return newState;
        if (oldState==States.EXECUTING && newState==States.IDLE)
            return newState;
        if (oldState==States.EXECUTING && newState==States.FINISHED)
            return newState;
        if (oldState==States.FINISHED && newState==States.IDLE)
            return newState;
        return States.NONE;
    }

    public Boolean stateChangeRequst(States requestedState) {
        States oldState=currentState;
        States newState= checkValidTransition(oldState, requestedState);
        if (newState==States.NONE)
            return Boolean.FALSE;
        switch (oldState){}
        switch (newState){
            case IDLE -> {
                enterIdle();
            }
            case EXECUTING -> {
                enterExecuting();
            }
            case FINISHED -> {
                enterFinished();
            }
        }
        currentState=newState;
        return null;
    }
}
