package com.voinesti.versuriapp.repository;

import com.voinesti.versuriapp.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    
    // Pentru sortare alfabetică generală
    List<Song> findAllByOrderByTitleAsc();

    // Pentru sortare alfabetică pe categorie
    List<Song> findByCategoryOrderByTitleAsc(String category);

    // ACEASTA METODA LIPSEA (necesară pentru numărătoarea de pe butoane)
    List<Song> findByCategory(String category);
}