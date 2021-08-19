package pl.czekaj.springsocial.controller.controllerHelper;

import org.springframework.data.domain.Sort;

public class PageAndSortDirectionHelper {

    private PageAndSortDirectionHelper(){
    }

    public static int getPageNumberGreaterThenZeroAndNotNull(Integer page){
        int pageNumber = page != null && page >=0 ? page : 0;
        return pageNumber;
    }

    public static Sort.Direction getSortDirectionNotNullAndDESC(Sort.Direction sort){
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.DESC;
        return sortDirection;
    }

    public static Sort.Direction getSortDirectionNotNullAndASC(Sort.Direction sort){
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        return sortDirection;
    }
}
