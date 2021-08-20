package pl.czekaj.springsocial.controller.controllerHelper;

import org.springframework.data.domain.Sort;

public class SortDirectionHelper {

    private SortDirectionHelper(){
    }

    public static Sort.Direction getSortDirection(Sort.Direction sortDirection){

        if(sortDirection == null) return getSortDirectionNotNullAndDESC(sortDirection);

        if(sortDirection.equals(Sort.Direction.ASC)){
            return getSortDirectionNotNullAndASC(sortDirection);
        } else return getSortDirectionNotNullAndDESC(sortDirection);

    }

    private static Sort.Direction getSortDirectionNotNullAndASC(Sort.Direction sort){
        return sort != null ? sort : Sort.Direction.ASC;
    }

    private static Sort.Direction getSortDirectionNotNullAndDESC(Sort.Direction sort){
        return sort != null ? sort : Sort.Direction.DESC;
    }
}
