package pl.czekaj.springsocial.controller.controllerHelper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class PageHelper {

    private PageHelper(){
    }

    public static int getPageNumberGreaterThenZeroAndNotNull(Integer page){
        return page != null && page >=0 ? page : 0;
    }
}
