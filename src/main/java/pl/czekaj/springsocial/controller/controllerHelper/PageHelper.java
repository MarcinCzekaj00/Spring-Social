package pl.czekaj.springsocial.controller.controllerHelper;

public class PageHelper {

    private PageHelper(){
    }

    public static int getPageNumberGreaterThenZeroAndNotNull(Integer page){
        return page != null && page >=0 ? page : 0;
    }
}
