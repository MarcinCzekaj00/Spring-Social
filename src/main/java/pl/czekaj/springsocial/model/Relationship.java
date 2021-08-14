package pl.czekaj.springsocial.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pl.czekaj.springsocial.model.modelHelper.RelationshipSerializer;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonSerialize(using = RelationshipSerializer.class)
public class Relationship extends RepresentationModel<Relationship> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipId;

    private Long fromUserId;

    private Long toUserId;

}
